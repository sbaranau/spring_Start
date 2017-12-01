package julia.web.mvc.group;

import julia.entity.Group;
import julia.entity.Role;

import javax.validation.Valid;
import java.util.List;

public class GroupForm {

    @Valid
    private Group group;
    private List<Role> roles;
    private List<Long> groupRoles;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Long> getGroupRoles() {
        return groupRoles;
    }

    public void setGroupRoles(List<Long> groupRoles) {
        this.groupRoles = groupRoles;
    }
}
