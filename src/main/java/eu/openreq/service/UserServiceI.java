package eu.openreq.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import eu.openreq.dbo.UserDbo;
import eu.openreq.dbo.UserRegistrationDbo;

public interface UserServiceI extends UserDetailsService {
    UserDbo findByEmail(String email);
    UserDbo save(UserRegistrationDbo registration, String confirmationKey, boolean confirmed);
}
