package eu.openreq.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.openreq.Util.Utils;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.ReleaseRepository;
import eu.openreq.repository.RequirementRepository;
import eu.openreq.repository.SkillRepository;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.UserService;

@Controller
public class DefaultController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private RequirementRepository requirementRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/aboutus")
    public String greetingForm(Model model) {
        return "aboutus";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

	@Value("${superuser.username}")
	private String superUserUserName;

	@Value("${superuser.hashedPassword}")
	private String superUserHashedPassword;

	@Value("${superuser.firstName}")
	private String superUserFirstName;

	@Value("${superuser.lastName}")
	private String superUserLastName;

	@Value("${superuser.email}")
	private String superUserEmail;

    @GetMapping("/")
    public String root(Authentication authentication, HttpServletResponse http) {
    	//System.out.println("changeMe01637085 -> " + passwordEncoder.encode("changeMe01637085"));
    	Utils.createSuperuser(superUserUserName, superUserHashedPassword, superUserFirstName, superUserLastName,
                superUserEmail, userRepository);
        Utils.createDefaultUsers(userService, userRepository);
		//Utils.createDefaultProjects(superUserUserName, projectRepository, releaseRepository, userService, userRepository, passwordEncoder, skillRepository, requirementRepository);

        if ((authentication != null) && authentication.isAuthenticated()) {
			try {
				http.sendRedirect("/project/list");
			} catch (IOException ex) {}
		}
        return "index";
    }

	@RequestMapping("/default")
	public void defaultPage(Model model, HttpServletResponse http) {
		try {
			http.sendRedirect("/project/list");
		} catch (IOException ex) {}
	}

	@GetMapping("/privacypolicy")
	public String privacyPolicyPage(Model model) {
		return "privacy_policy";
	}

    @GetMapping("/user")
    public String user() {
        return "user/index";
    }

}
