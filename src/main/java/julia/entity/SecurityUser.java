package julia.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author Ivan Shyrma
 */
public class SecurityUser extends NamedEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String surname;

    private String phone;

    private Long photo;

    private Long statusId;

    private List<GrantedAuthority> authorities;

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Получает photo.
     * @return photo
     */
    public Long getPhoto() {
        return photo;
    }

    /**
     * Устанавливает photo.
     * @param photo photo
     */
    public void setPhoto(Long photo) {
        this.photo = photo;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
