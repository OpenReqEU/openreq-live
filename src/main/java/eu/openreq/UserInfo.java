package eu.openreq;

import java.util.Collection;

import eu.openreq.Util.Utils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserInfo extends User {

	private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImagePath;

    public UserInfo(String username, String password, String firstName, String lastName, String profileImagePath,
                    boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                    Collection<? extends GrantedAuthority> authorities)
    {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileImagePath = profileImagePath;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
    		return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getShortFullName(int maxLength) {
        return Utils.getShortFullName(firstName, lastName, maxLength);
    }

    public String getProfileImagePath() {
        return (profileImagePath != null) ? profileImagePath : "/images/userimage.png";
    }

}
