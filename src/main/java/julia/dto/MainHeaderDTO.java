package julia.dto;

/**
 * DTO for header
 *
 * @author Pudul Yuriy
 */
public class MainHeaderDTO {

    /**
     * Наименование организации
     */
    private String organization;
    /**
     * Наименование пользователя
     */
    private String userName;
    /**
     * id пользователя
     */
    private Long userId;


    public MainHeaderDTO() {}

    public MainHeaderDTO(String organization, String userName) {
        this.organization = organization;
        this.userName = userName;
    }


    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
