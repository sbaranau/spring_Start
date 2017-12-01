package julia.dto.group;

import julia.entity.Group;

import java.util.List;

/**
 * DTO создания новой группы
 *
 * @author Pudul Yuriy
 */
public class GroupRequestDTO {

    /**группа*/
    private Group group;

    /**Список id грантов принадлежащих группе*/
    private List<Long> roles;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }
}
