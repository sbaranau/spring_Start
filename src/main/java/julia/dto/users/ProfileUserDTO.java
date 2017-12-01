package julia.dto.users;

/**
 * DTO пользователей профиля
 *
 * @author Pudul Yuriy
 */
public class ProfileUserDTO {

    /**id пользователя*/
    private Long userId;
    /**id отдела*/
    private Long departmentId;
    /**id должности*/
    private Long postId;
    /**Является ли пользователь обучающим*/
    private boolean isTeacher = false;
    /**id статуса пользователя*/
    private Long statusId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
