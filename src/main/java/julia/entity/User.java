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

    private Date dateBirth;

    private Long photo;


    private Long statusId;

    /**Логическа переменная, статус "Руководитель" YES/NO*/
    private boolean chief;

    /**Логическа переменная, признак "Пароль по умолчанию" YES/NO*/
    private boolean defaultPassword;

    private Long trainingFinalDataId;

    private String trainingDescription;

    private String trainingResult;

    private Boolean trainingVisited = true;

    private Long departmentId;

    // here because JACKSON is being a shit
    private String fullName;
    private String nameMiddleName;
    private String shortFullName;
    private String fullNameWithInitials;

    public Long getTrainingFinalDataId() {
        return trainingFinalDataId;
    }

    public void setTrainingFinalDataId(Long trainingFinalDataId) {
        this.trainingFinalDataId = trainingFinalDataId;
    }

    public String getTrainingDescription() {
        return trainingDescription;
    }

    public void setTrainingDescription(String trainingDescription) {
        this.trainingDescription = trainingDescription;
    }

    public String getTrainingResult() {
        return trainingResult;
    }

    public void setTrainingResult(String trainingResult) {
        this.trainingResult = trainingResult;
    }

    public Boolean getTrainingVisited() {
        return trainingVisited;
    }

    public void setTrainingVisited(Boolean trainingVisited) {
        this.trainingVisited = trainingVisited;
    }

    public boolean getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(boolean defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    /**
     * Получает photo.
     * 
     * @return photo
     */
    public Long getPhoto() {
        return photo;
    }

    /**
     * Устанавливает photo.
     * 
     * @param photo
     *            photo
     */
    public void setPhoto(Long photo) {
        this.photo = photo;
    }

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

    /**
     * Получает dateBirth.
     * 
     * @return dateBirth
     */
    public Date getDateBirth() {
        return dateBirth;
    }

    /**
     * Устанавливает dateBirth.
     * 
     * @param dateBirth
     *            dateBirth
     */
    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getFullNameWithInitials() {
        return this.surname + " " + this.name.charAt(0) + ". " + this.middleName.charAt(0) + ".";
    }

    @JsonIgnore
    public void setFullNameWithInitials() {
    }


    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isChief() {
        return chief;
    }

    public void setChief(boolean chief) {
        this.chief = chief;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (chief != user.chief) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (middleName != null ? !middleName.equals(user.middleName) : user.middleName != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (dateBirth != null ? !dateBirth.equals(user.dateBirth) : user.dateBirth != null) return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (statusId != null ? !statusId.equals(user.statusId) : user.statusId != null) return false;
        if (trainingFinalDataId != null ? !trainingFinalDataId.equals(user.trainingFinalDataId) : user.trainingFinalDataId != null)
            return false;
        if (trainingDescription != null ? !trainingDescription.equals(user.trainingDescription) : user.trainingDescription != null)
            return false;
        if (trainingResult != null ? !trainingResult.equals(user.trainingResult) : user.trainingResult != null)
            return false;
        if (trainingVisited != null ? !trainingVisited.equals(user.trainingVisited) : user.trainingVisited != null)
            return false;
        if (departmentId != null ? !departmentId.equals(user.departmentId) : user.departmentId != null) return false;
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
        result = prime * result + ((dateBirth == null) ? 0 : dateBirth.hashCode());
        result = prime * result + ((departmentId == null) ? 0 : departmentId.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((photo == null) ? 0 : photo.hashCode());
        result = prime * result + ((statusId == null) ? 0 : statusId.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((trainingDescription == null) ? 0 : trainingDescription.hashCode());
        result = prime * result + ((trainingFinalDataId == null) ? 0 : trainingFinalDataId.hashCode());
        result = prime * result + ((trainingResult == null) ? 0 : trainingResult.hashCode());
        result = prime * result + ((trainingVisited == null) ? 0 : trainingVisited.hashCode());
        return result;
    }
}
