package eu.openreq.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import eu.openreq.service.UserService;

@Component
public final class LoginSuccessHandler implements AuthenticationSuccessHandler
{
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		User userInfo = (User) authentication.getPrincipal();
        this.userService.updateLastLoginDateForUserByName(userInfo.getUsername());
        return;

        /*
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("admin_side/Home.htm");
            return;
        }
        */
    }
}
