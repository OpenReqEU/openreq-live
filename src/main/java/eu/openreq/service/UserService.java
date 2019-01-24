package eu.openreq.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eu.openreq.UserInfo;
import eu.openreq.dbo.RoleDbo;
import eu.openreq.dbo.UserDbo;
import eu.openreq.dbo.UserRegistrationDbo;
import eu.openreq.repository.UserRepository;

@Service
public class UserService implements UserServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDbo userDbo = userRepository.findOneByUsername(username);

        if (userDbo == null) {
            throw new UsernameNotFoundException("Invalid username or password." + username);
        }

        return new UserInfo(
        		userDbo.getUsername(),
        		userDbo.getPassword(),
        		userDbo.getFirstName(),
        		userDbo.getLastName(),
        		userDbo.getProfileImagePath(),
        		userDbo.isEnabled(),
        		true, // accountNonExpired
        		true, // credentialsNonExpired
        		true, // accountNonLocked
        		mapRolesToAuthorities(userDbo.getRoles())
        	);
    }

    public void updateLastLoginDateForUserByName(String username) {
        UserDbo userDbo = userRepository.findOneByUsername(username);
        userDbo.updateLastLoginDate();
        userRepository.save(userDbo);
    }

    public List<UserDbo> searchUser(String searchTerm, boolean includeEmailAddress) {
        if (searchTerm.contains(" ")) {
            if (searchTerm.contains("(")) {
                String[] searchTermParts = searchTerm.split("\\(");
                if (searchTermParts.length > 1) {
                    searchTerm = searchTermParts[1].replace(")", "");
                } else {
                    searchTerm = searchTermParts[0];
                    searchTermParts = searchTerm.split(" ");
                    searchTerm = searchTermParts[0];
                }
            } else {
                String[] searchTermParts = searchTerm.split(" ");
                searchTerm = searchTermParts[0];
            }
        }
        if (includeEmailAddress) {
            return userRepository.findUserBySearchTerm(searchTerm);
        }
        return userRepository.findUserBySearchTermExcludeEmailAddress(searchTerm, "@");
    }

    public UserDbo findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public UserDbo findByEmail(String email) {
        return userRepository.findOneByMailAddress(email);
    }

    public void setUserRepository(UserRepository userRepository) {
    		this.userRepository = userRepository;
    }

    @SuppressWarnings("serial")
	@Override
    public UserDbo save(UserRegistrationDbo registration, String confirmationKey, boolean confirmed) {
        UserDbo user = new UserDbo();
        user.setUsername(registration.getUsername());
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setMailAddress(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(new HashSet<RoleDbo>() {{ add(new RoleDbo(RoleDbo.Role.ROLE_STAKEHOLDER)); }});
        user.setConfirmationKey(confirmationKey);
        user.setEnabled(confirmed);
        return userRepository.save(user);
    }

    public UserDbo changePassword(UserDbo user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordKey(null);
        user.setLastResetPasswordKeyDate(null);
        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleDbo> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().toString()))
                .collect(Collectors.toList());
    }
}
