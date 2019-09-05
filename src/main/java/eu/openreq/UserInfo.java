package eu.openreq;

import java.util.Collection;

import eu.openreq.Util.Utils;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Data
public class UserInfo extends User {

	private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImagePath;

    public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired,
                    boolean credentialsNonExpired, boolean accountNonLocked,
                    Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.username = username;
		this.password = password;
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
