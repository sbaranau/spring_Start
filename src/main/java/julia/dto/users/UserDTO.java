package julia.dto.users;

/**
 * DTO объект с данными для списка пользователей
 *
 * @author Pudul Yuriy
 */
public class UserDTO {

    /**id пользователя*/
    private Long userId;
    /**id статуса пользователя*/
    private Long userStatusId;
    /**Полное имя пользователя ФИО*/
    private String fullName;
    /**id файла с фотографией пользователя*/
    private Long photoId;
    /**статус-чек руководитель YES/NO */
    private boolean chief;
    /**id отдела*/
    private Long departmentId;
    /**Наименование отдела*/
    private String departmentName;
    /**id родительского отдела*/
    private Long departmentParentId;
    /**Наименование родительского отдела*/
    private String departmentParentName;
    /**id должности пользователя*/
    private Long positionId;
    /**Наименование должности*/
    private String positionName;
    /**login пользователя*/
    private String login;

    public boolean isChief() {
        return chief;
    }

    public void setChief(boolean chief) {
        this.chief = chief;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Long userStatusId) {
        this.userStatusId = userStatusId;
    }

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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentParentId() {
        return departmentParentId;
    }

    public void setDepartmentParentId(Long departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    public String getDepartmentParentName() {
        return departmentParentName;
    }

    public void setDepartmentParentName(String departmentParentName) {
        this.departmentParentName = departmentParentName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
