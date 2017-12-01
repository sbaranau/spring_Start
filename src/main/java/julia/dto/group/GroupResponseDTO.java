package julia.dto.group;

import julia.entity.Group;

import java.util.List;

/**
 * DTO ответа редактирования группы
 *
 * @author Pudul Yuriy
 */
public class GroupResponseDTO {

    /**группа*/
    private Group group;

    /**Список id грантов принадлежащих группе*/
    private List<Long> roles;

    /**Список ролей для группы*/
    private List<RolesForGroupDTO> rolesForGroup;

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

    public List<RolesForGroupDTO> getRolesForGroup() {
        return rolesForGroup;
    }

    public void setRolesForGroup(List<RolesForGroupDTO> rolesForGroup) {
        this.rolesForGroup = rolesForGroup;
    }
}
