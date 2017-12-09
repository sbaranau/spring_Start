package julia.entity;

import by.tehnologia.regexp.Regexp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author Ivan Shyrma
 * @author Aleksandr Golovnyov
 * @author Andrey Gorbachov
 */

public class User extends Entity implements INamedEntity {

    private static final long serialVersionUID = 1L;

    @Length(max = 64)
    private String login;

    @JsonIgnore
    private String password;

    @NotBlank
    @Length(max = 32)
    private String name;

    @NotBlank
    @Length(max = 32)
    private String surname;

    @NotBlank
    @Length(max = 32)
    private String middleName;

    @NotBlank
    @Pattern(regexp = "\\+\\d{10,12}")
    @Length(max = 64)
    private String phone;

    @Length(max = 64)
    @Pattern(regexp = Regexp.EMAIL)
    private String email;


    // here because JACKSON is being a shit
    private String fullName;
    private String nameMiddleName;
    private String shortFullName;
    private String fullNameWithInitials;


    public String getNameMiddleName() {
        return getName() + " " + getMiddleName();
    }

    @JsonIgnore
    public void setNameMiddleName() {
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname
     *            the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null) {
            phone = phone.replaceAll("\\(|\\)|\\s|-", "");
        }

        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return this.surname + " " + this.name + " " + this.middleName;
    }

    @JsonIgnore
    public void setFullName() {
    }

    public String getShortFullName() {
        return this.surname + " " + this.name.charAt(0) + "." + this.middleName.charAt(0) + ".";
    }

    @JsonIgnore
    public void setShortFullName() {
    }


    public String getFullNameWithInitials() {
        return this.surname + " " + this.name.charAt(0) + ". " + this.middleName.charAt(0) + ".";
    }

    @JsonIgnore
    public void setFullNameWithInitials() {
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (middleName != null ? !middleName.equals(user.middleName) : user.middleName != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
           if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        if (nameMiddleName != null ? !nameMiddleName.equals(user.nameMiddleName) : user.nameMiddleName != null)
            return false;
        if (shortFullName != null ? !shortFullName.equals(user.shortFullName) : user.shortFullName != null)
            return false;
        return fullNameWithInitials != null ? fullNameWithInitials.equals(user.fullNameWithInitials) : user.fullNameWithInitials == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }
}
