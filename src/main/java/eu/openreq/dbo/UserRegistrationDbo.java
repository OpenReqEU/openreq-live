package eu.openreq.dbo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import eu.openreq.security.constraint.FieldMatch;

import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
    @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserRegistrationDbo {

    @NotEmpty
    @Size(min=2, max=255)
    private String firstName;

    @NotEmpty
    @Size(min=2, max=255)
    private String lastName;

    @NotEmpty
    @Size(min=2, max=255)
    private String username;

    @NotEmpty
    @Size(min=6, max=255)
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    @Size(min=5, max=255)
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;

    public UserRegistrationDbo() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

}
