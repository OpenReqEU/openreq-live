package eu.openreq.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserProfileForm {

    @NotEmpty
    @Size(min=2, max=255)
    private String firstName;

    @NotEmpty
    @Size(min=2, max=255)
    private String lastName;

    @NotEmpty
    @Size(min=2, max=255)
    private String username;

    @Size(max=255)
    private String password;

    private String confirmPassword;

    @Email
    @NotEmpty
    @Size(min=5, max=255)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
