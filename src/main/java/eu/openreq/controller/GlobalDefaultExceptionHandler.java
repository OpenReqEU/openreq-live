package eu.openreq.controller;

import eu.openreq.Util.Utils;
import eu.openreq.dbo.UserDbo;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.EmailService;
import eu.openreq.service.IPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e, Authentication authentication) throws Exception {
        List<String> stackTraceElements = new ArrayList<>();
        for (StackTraceElement element : e.getStackTrace()) {
            stackTraceElements.add(element.toString());
        }

        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        String clientIpAddress = request.getRemoteAddr();
        String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
        String userAgentInfo = request.getHeader("User-Agent");

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
        StringBuilder header = new StringBuilder("<ul>");
        header.append("<li><b>Time:</b> " + dateformat.format(new Date()) + "</li>");
        header.append("<li><b>IP Address</b> " + clientIpAddress + "</li>");
        header.append("<li><b>Forwarded IP Address:</b> " + forwardedIpAddress + "</li>");
        header.append("<li><b>User Agent:</b> " + userAgentInfo + "</li>");
        header.append("<li><b>Method:</b> " + request.getMethod() + "</li>");
        header.append("<li><b>URL:</b> " + request.getRequestURL() + "</li>");
        header.append("<li><b>Session ID:</b> " + request.getRequestedSessionId() + "</li>");
        header.append("<li><b>Query String:</b> " + request.getQueryString() + "</li>");

        for (Enumeration<?> enumeration = request.getHeaderNames(); enumeration.hasMoreElements();) {
            String headerName = (String) enumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            header.append("<li><b>" + headerName + "</b>: " + headerValue + "</li>");
        }

        StringBuilder userDetails = new StringBuilder("<li><b>Authenticated user:</b> NO</li>");
        if (currentUser != null) {
            userDetails = new StringBuilder("<li><b>Authenticated user:</b> YES</li>");
            userDetails.append("<li><b>Name:</b> ");
            userDetails.append(currentUser.getFirstName());
            userDetails.append(" ");
            userDetails.append(currentUser.getLastName());
            userDetails.append("</li>");
            userDetails.append("<li><b>Username:</b> ");
            userDetails.append(currentUser.getUsername());
            userDetails.append("</li>");
            userDetails.append("<li><b>Mail address:</b> ");
            userDetails.append(currentUser.getMailAddress());
            userDetails.append("</li>");
        }

        header.append(userDetails);
        header.append("</ul>");

        emailService.sendEmailAsync(
                "ralleaustria@gmail.com",
                "[OpenReq!Live] Exception!!! " + e.getMessage(),
                "<b style='color:darkred;'>Exception message: " + e.getMessage()
                        + "</b><br /><br />" + header.toString()
                        + "<br /><br /><b>Stack Trace:</b><br />"
                        + "<code style=\"font-size:8px;\">"
                        + String.join("<br />", stackTraceElements)
                        + "</code>",
                "Exception message: " + e.getMessage()
                        + "\n\n"
                        + userDetails.toString()
                        + "\n\n"
                        + String.join("\n", stackTraceElements));

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
