package julia.dto.users;

/**
 * DTO объект с данными для списка пользователей
 *
 * @author sergey
 */
public class UserDTO {

    /**id пользователя*/
    private Long userId;
    /**Полное имя пользователя ФИО*/
    private String fullName;
    /**id файла с фотографией пользователя*/
    private Long photoId;
    /**login пользователя*/
    private String login;
    /** users phone*/
    private String phone;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
