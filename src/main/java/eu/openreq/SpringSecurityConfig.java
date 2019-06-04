package eu.openreq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import eu.openreq.handler.LoginSuccessHandler;
import eu.openreq.service.UserServiceI;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceI userService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
	public void configure(WebSecurity web) throws Exception {
    		web.debug(false);
    }

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
				.csrf().disable()
                .authorizeRequests()
					.antMatchers(
						"/",
						"/home",
						"/aboutus",
						"/privacypolicy",
						"/registration",
						"/registration/user/**/confirm/**",
						"/registration/user/**/confirm/**/finish",
						"/.well-known/acme-challenge/**",
						"/forgotpassword",
						"/user/**/resetpassword/k/**",
						"/user/keepalive",
                        "/product/list",
                        "/swagger-**",
						"/project/p/**/manage",
						"/project/list",
						"/project/create",
						"/upload/**",
						"/project/**/delete",
						"/project/generate",
						"/project/generatedependencyproject",
						"/project/generatealloadstudyprojects",
						"/project/upload",
						"/project/**/export",
						"/project/**/import",
						"/project/**/checkconsistency.json",
						"/project/**/user/list.json",
						"/project/**/update.json",
						"/project/**/settings.json",
						"/project/**/settings/update.json",
						"/project/**/issue/list.json",
						"/project/**/requirement/rating/attribute/list.json",
						"/project/**/requirement/pairs/recommend",
						"/project/**/requirement/pairs/recommend.json",
						"/project/**/requirement/vote.json",
						"/project/**/requirement/list.json",
						"/project/**/requirement/create.json",
						"/project/**/requirement/update.json",
						"/project/**/requirement/delete.json",
						"/project/**/requirement/**/user/**/assign.json",
						"/project/**/requirement/**/user/**/unassign.json",
						"/project/**/requirement/**/vote/delegate.json",
						"/project/**/requirement/**/vote/delegation/remove.json",
						"/project/**/requirement/recommend/similar",
						"/project/**/dependency/recommend",
						"/project/**/release/list.json",
						"/project/**/release/create.json",
						"/project/**/release/update.json",
						"/project/**/release/delete.json",
                        "/project/**/dependency/list.json",
                        "/project/**/dependency/create.json",
                        "/project/**/dependency/delete.json",
						"/project/**/statistics/chart/optimalreleaseplan",
						"/project/**/statistics/graph/dependency",
                        "/api/v1/**",
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security",
                        "/webjars/springfox-swagger-ui/**"
                    ).permitAll()
					.antMatchers("/admin/**").hasAnyRole("ADMIN")
					.antMatchers("/user/**").hasAnyRole("ADMIN", "STAKEHOLDER", "REQUIREMENTS_MANAGER")
					.antMatchers("/project/**").hasAnyRole("ADMIN", "STAKEHOLDER", "REQUIREMENTS_MANAGER")
					.anyRequest()
					.authenticated()
                .and()
                .formLogin()
					.loginPage("/login")
					.successHandler(loginSuccessHandler)
					.defaultSuccessUrl("/user/loggedin")
					.permitAll()
				.and()
                .logout()
					.permitAll()
					.invalidateHttpSession(true)
                     .clearAuthentication(true)
                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                     .logoutSuccessUrl("/login?logout")
				.and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
        http.headers()
				.frameOptions()
				.disable()
				.addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM live.openreq.eu"));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

}
