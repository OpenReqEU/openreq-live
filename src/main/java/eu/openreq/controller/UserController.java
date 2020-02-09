package eu.openreq.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import eu.openreq.UserInfo;
import eu.openreq.Util.Constants;
import eu.openreq.Util.Utils;
import eu.openreq.dbo.ProjectDbo;
import eu.openreq.form.UserProfileForm;
import eu.openreq.form.UserResetPasswordForm;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.service.IPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import eu.openreq.dbo.UserDbo;
import eu.openreq.dbo.UserRegistrationDbo;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.EmailService;
import eu.openreq.service.UserService;
import org.springframework.web.client.HttpServerErrorException;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IPService ipService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public UserRegistrationDbo userRegistrationDto() {
        return new UserRegistrationDbo();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(@ModelAttribute("user") UserRegistrationDbo userDbo,
                                       @RequestParam(value="uid", required=false) Long userID,
                                       Model model, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        if (currentUser != null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
                    "Please log out first before you try to register a new user account!");
        }

    	model.addAttribute("registeredUser", (userID != null) ? userRepository.findOne(userID) : null);
        return "registration";
    }

    @GetMapping("/user/loggedin")
    public String loggedIn(HttpServletRequest request, HttpServletResponse http, Authentication authentication) {
        if ((authentication == null) || !authentication.isAuthenticated()) {
            try {
                http.sendRedirect("/");
                return null;
            } catch (IOException ex) {}
            return "redirect:/";
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("loggedIn") != null) {
            System.out.println("You are already loggedin!");
            return "redirect:/";
        }

        String clientIpAddress = request.getRemoteAddr();
        String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
        String userAgentInfo = request.getHeader("User-Agent");

        session.setAttribute("loggedIn", true);
        User userInfo = (User) authentication.getPrincipal();
        UserDbo currentUser = userRepository.findOneByUsername(userInfo.getUsername());
        currentUser.logLogin(clientIpAddress, request.getRequestedSessionId(), forwardedIpAddress, userAgentInfo);
        currentUser.updateLastLoginDate();
        userRepository.save(currentUser);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/user/keepalive")
    public Map<String, Object> keepalive(Authentication authentication, HttpServletResponse http) {
        UserDbo currentUser = null;
        Map<String, Object> result = new HashMap<>();

        if ((authentication == null) || !authentication.isAuthenticated()) {
            result.put("error", true);
            result.put("errorMessage", "You are not logged in!");
            return result;
        }

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        currentUser = userRepository.findOneByUsername(userInfo.getUsername());
        result.put("error", false);
        result.put("userID", currentUser.getId());
        return result;
    }

    @GetMapping("/forgotpassword")
    public String forgotPasswordForm(@ModelAttribute("reset") UserResetPasswordForm userResetPasswordForm,
                                     Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        if (currentUser != null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
                    "Please log out first before you try to reset your password!");
        }

        return "forgot_password";
    }

    @PostMapping("/forgotpassword")
    public String sendResetPasswordLink(@ModelAttribute("reset") @Valid UserResetPasswordForm userResetPasswordForm,
                                        BindingResult result, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        if (currentUser != null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
                    "Please log out first before you try to reset your password!");
        }

        if (result.hasErrors()){
            return "forgot_password";
        }

        UserDbo user = userService.findByEmail(userResetPasswordForm.getEmail());
        if (user == null) {
            return "forgot_password"; // don't show error in case the user does not exist (for security reasons)!
        }

        Calendar date = Calendar.getInstance();
        long now = date.getTimeInMillis();
        Date afterRemovingTenMins = new Date(now - (Constants.PASSWORD_EXPIRES_IN_MINUTES * Constants.ONE_MINUTE_IN_MILLIS));

        if (user.getLastResetPasswordKeyDate() == null || user.getLastResetPasswordKeyDate().before(afterRemovingTenMins)) {
            user.setResetPasswordKey(Utils.generateRandomResetPasswordKey());
            user.setLastResetPasswordKeyDate(new Date());
            userRepository.save(user);
        } else if (user.getLastResetPasswordKeyDate() != null && user.getLastResetPasswordKeyDate().after(afterRemovingTenMins)) {
            return "forgot_password"; // don't show error in case the user does not exist (for security reasons)!
        }

        assert(user.getResetPasswordKey() != null);

        String htmlMessage = "<p><strong>Hello " + user.getFirstName() + " " + user.getLastName() + "</strong>,</p>\n" +
                "<p>someone requested a new password for your InnoSensr account.\n" +
                "Please click on the following link to reset your password:</p>\n" +
                "<hr />\n" +
                "<a class=\"btn\" href=\"https://" + ipService.getHost() + ":" + ipService.getPort() + "/user/" + user.getId() + "/resetpassword/k/" + user.getResetPasswordKey() + "\">Reset Password</a>\n" +
                "<p><b>Please keep in mind that this link is only valid for " + Constants.PASSWORD_EXPIRES_IN_MINUTES + " minutes.</b></p>" +
                "<p>If you didn't request a password reset then you can safely ignore this email. " +
                "Only a person who has access to your email account can reset the password of your InnoSensr account.</p>";

        emailService.sendEmailAsync(
                user.getMailAddress(),
                "Reset Password",
                htmlMessage,
                "Hello " + user.getFirstName() + " " + user.getLastName() + ",\n" +
                        "someone requested a new password for your InnoSensr account.\n\n" +
                        "Please click on the following link to reset your password:\n\n" +
                        "https://" + ipService.getHost() + ":" + ipService.getPort() + "/user/" + user.getId() + "/resetpassword/k/" + user.getResetPasswordKey() + "\n\n" +
                        "If you didn't request a password reset then you can safely ignore this email. " +
                        "Only a person who has access to your email account can reset the password of your InnoSensr account."
        );
        return "forgot_password";
    }

    @GetMapping("/user/{userID}/resetpassword/k/{resetPasswordKey}")
    public String resetPassword(@PathVariable(value="userID") long userID,
                                @PathVariable(value="resetPasswordKey") String resetPasswordKey,
                                Model model, HttpServletRequest request, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        UserDbo user = userRepository.findOne(userID);
        if (currentUser != null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
                    "Please log out first before you try to reset your password!");
        }

        if (user == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "The user does not exist!");
            return "reset_password";
        }

        if (user.getResetPasswordKey() == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "The given link is invalid or has already " +
                    "expired! Please try to reset your password again.");
            return "reset_password";
        }

        Calendar date = Calendar.getInstance();
        long now = date.getTimeInMillis();
        Date afterRemovingTenMins = new Date(now - (Constants.PASSWORD_EXPIRES_IN_MINUTES * Constants.ONE_MINUTE_IN_MILLIS));

        if (user.getLastResetPasswordKeyDate() == null || user.getLastResetPasswordKeyDate().before(afterRemovingTenMins)) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "The given link has already expired! Please " +
                    "try to reset your password again.");
            return "reset_password";
        }

        final String newPassword = Utils.generateRandomPassword();
        userService.changePassword(user, newPassword);

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, newPassword, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            //log.debug(String.format("Auto login %s successfully!", email));
        }
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        model.addAttribute("error", false);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("newPassword", newPassword);
        return "reset_password";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDbo userRegistrationDbo,
                                      BindingResult result, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        if (currentUser != null) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
                    "Please log out first before you try to register a new user account!");
        }

        UserDbo existing = userService.findByUsername(userRegistrationDbo.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username!");
        }

        existing = userService.findByEmail(userRegistrationDbo.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email!");
        }

        if (result.hasErrors()){
            return "registration";
        }

        InetAddress ip = null;
        String hostname = null;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        } catch (UnknownHostException e) {
            logger.error("An exception occurred.", e);
        }

        final String confirmationKey = UUID.randomUUID().toString();
        UserDbo userDbo = userService.save(userRegistrationDbo, confirmationKey, false);
		String htmlMessage = "<p><strong>Hello " + userRegistrationDbo.getFirstName() + " " + userRegistrationDbo.getLastName() + "</strong>,</p>\n" +
                             "<p>thanks for signing up for InnoSensr. You are now a registered user on InnoSensr.\n" +
                             "Please confirm your account by clicking on the following link:</p>\n" +
                             "<hr />\n" +
                             "<a class=\"btn\" href=\"https://" + ipService.getHost() + ":" + ipService.getPort() + "/registration/user/" + userDbo.getId() + "/confirm/" + confirmationKey + "\">Confirm Account</a>\n";

		emailService.sendEmailAsync(
			userRegistrationDbo.getEmail(),
			"Welcome to InnoSensr",
			htmlMessage,
			"Hello " + userRegistrationDbo.getFirstName() + " " + userRegistrationDbo.getLastName() + ",\n" +
                    "thanks for signing up for InnoSensr. You are now a registered user on InnoSensr.\n" +
                    "Please confirm your account by clicking on the following link:\n" +
			        "https://" + ipService.getHost() + ":" + ipService.getPort() + "/registration/user/" + userDbo.getId() + "/confirm/" + confirmationKey
		);
        return "redirect:/registration?success&uid=" + userDbo.getId();
    }

    @GetMapping("/registration/user/{userID}/confirm/{confirmKey}")
    public String confirmSummary(
            HttpServletRequest request,
            @PathVariable(value="userID") Long userID,
            @PathVariable(value="confirmKey") String confirmKey,
            Model model,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        UserDbo userDbo = userRepository.findOne(userID);

        if ((currentUser != null) && (currentUser != userDbo)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        if (userDbo == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, "This user does not exist!");
        }

        if (userDbo.getConfirmationKey() == null) {
        		System.out.println("Account already confirmed!");
			model.addAttribute("registerConfirm", "alreadyconfirmed");
			return "registration_confirm";
        }

        if (!userDbo.getConfirmationKey().equals(confirmKey)) {
        		System.out.println("Invalid confirmation key given!");
    			model.addAttribute("registerConfirm", "invalidconfirmationkey");
    			return "registration_confirm";
        }

		model.addAttribute("user", userDbo);
		return "registration_summary";
    }

    @PostMapping("/registration/user/{userID}/confirm/{confirmKey}/finish")
    public String confirmAccount(
            HttpServletRequest request,
            @PathVariable(value="userID") Long userID,
            @PathVariable(value="confirmKey") String confirmKey,
            Model model, Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        UserDbo userDbo = userRepository.findOne(userID);

        if ((currentUser != null) && (currentUser != userDbo)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        if (userDbo == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, "This user does not exist!");
        }

        if (userDbo.getConfirmationKey() == null) {
        	System.out.println("Account already confirmed!");
			model.addAttribute("registerConfirm", "alreadyconfirmed");
			return "registration_confirm";
        }

        if (!userDbo.getConfirmationKey().equals(confirmKey)) {
            System.out.println("Invalid confirmation key given!");
            model.addAttribute("registerConfirm", "invalidconfirmationkey");
            return "registration_confirm";
        }

		userDbo.setEnabled(true);
		userDbo.setConfirmationKey(null);
		userRepository.save(userDbo);
		System.out.println("Account successfully confirmed!");
		model.addAttribute("registerConfirm", "confirmed");
		return "registration_confirm";
    }

    @GetMapping("/user/profile")
    public String createUserProfileForm(HttpServletRequest request, Model model, UserProfileForm userProfileForm, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        if (currentUser == null) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        userProfileForm.setFirstName(currentUser.getFirstName());
        userProfileForm.setLastName(currentUser.getLastName());
        userProfileForm.setEmail(currentUser.getMailAddress());
        userProfileForm.setUsername(currentUser.getUsername());
        model.addAttribute("user", currentUser);
        return "profile_update";
    }

    @PostMapping("/user/profile")
    public String createProject(HttpServletRequest request, @Valid UserProfileForm userProfileForm,
                                BindingResult bindingResult, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        if (currentUser == null) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        UserDbo existingUser = userService.findByUsername(userProfileForm.getUsername());
        if ((existingUser != null) && (existingUser.getId() != currentUser.getId())) {
            bindingResult.rejectValue("username", null, "There is already another account with that username in use!");
        }

        existingUser = userService.findByEmail(userProfileForm.getEmail());
        if ((existingUser != null) && (existingUser.getId() != currentUser.getId())) {
            bindingResult.rejectValue("email", null, "There is already another account with that email address in use!");
        }

        if ((userProfileForm.getPassword().length() > 0) || (userProfileForm.getConfirmPassword().length() > 0)) {
            if (!userProfileForm.getPassword().equals(userProfileForm.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", null, "The passwords must be equal!");
            }
        }

        if (bindingResult.hasErrors()) {
            return "profile_update";
        }

        currentUser.setFirstName(userProfileForm.getFirstName());
        currentUser.setLastName(userProfileForm.getLastName());
        currentUser.setMailAddress(userProfileForm.getEmail());

        if (!currentUser.getUsername().equals(userProfileForm.getUsername())) {
            currentUser.setUsername(userProfileForm.getUsername());
            userInfo.setUsername(userProfileForm.getUsername());
            authentication = new UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), userInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        if ((userProfileForm.getPassword().length() > 0) || (userProfileForm.getConfirmPassword().length() > 0)) {
            currentUser.setPassword(passwordEncoder.encode(userProfileForm.getPassword()));
        }

        userRepository.save(currentUser);
        return "redirect:/project/list";
    }

    @ResponseBody
    @PostMapping("/user/search.json")
    public Map<String, Object> searchUser(HttpServletRequest request, @RequestParam(value="query") String query,
                                          @RequestParam(value="type") String type, @RequestParam(value="projectKey") String projectKey,
                                          Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        Map<String, Object> result = new HashMap<>();

        if (currentUser == null) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        List<UserDbo> foundUsers = userService.searchUser(query, true);
        foundUsers.sort(Comparator.comparing(UserDbo::getLastName));
        List<Map<String, Object>> userDataList = new ArrayList<>();
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectKey);
        Set<Long> alreadyParticipatingUserIDs = project.getUserParticipations()
                .stream()
                .map(p -> p.getUserId())
                .collect(Collectors.toSet());
        alreadyParticipatingUserIDs.add(project.getCreatorUser().getId());

        for (UserDbo user : foundUsers) {
            if (type.equals("projectInvitation") && alreadyParticipatingUserIDs.contains(user.getId())) {
                continue;
            }

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("firstName", user.getFirstName());
            userData.put("lastName", user.getLastName());
            userData.put("email", user.getMailAddress());
            userData.put("profileImagePath", user.getProfileImagePath());
            userDataList.add(userData);
        }

        result.put("users", userDataList);
        return result;
    }

}
