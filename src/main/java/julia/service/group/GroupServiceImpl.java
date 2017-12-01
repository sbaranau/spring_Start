package julia.service.group;

import julia.dao.GroupDao;
import julia.dao.RoleDao;
import julia.entity.Group;
import julia.entity.Role;
import julia.security.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolai_Tarasevich
 *
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private GroupService groupService;

    @Value("${group.default}")
    private String defaultGroupName;

    @Override
    public Group getGroup(Long id) {
        return groupDao.read(id);
    }

    @Override
    public List<Group> getGroups() {
        return groupDao.findAll();
    }

    @Override
    public void saveGroup(Group group) {
        if (group.getId() == null) {
            final Long maxId = groupDao.getMaxId();
            if (maxId == null) {
                group.setId(1L);
            } else {
                group.setId(maxId + 1);
            }
            groupDao.create(group);
        } else {
            groupDao.update(group);
        }
    }

    @Override
    public void deleteGroup(Long id) {
        roleDao.deleteGroupsRoles(id);
        groupDao.delete(id);
    }

    @Override
    public Role getRole(Long id) {
        return roleDao.read(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    @Override
    public List<Role> getRoles(Long uid) {
        return roleDao.findGroupsRoles(uid);
    }

    @Override
    public void setGroupRoles(Long uid, List<Role> roles) {
        roleDao.deleteGroupsRoles(uid);
        for (Role r : roles) {
            roleDao.setGroupsRoles(uid, r.getId());
        }
    }

    @Override
    public void setGroupRoleIds(Long uid, List<Long> roleIds) {
        roleDao.deleteGroupsRoles(uid);
        for (Long roleId : roleIds) {
            roleDao.setGroupsRoles(uid, roleId);
        }
    }

    @Override
    public Group findGroupByName(String name) {
        return groupDao.findByName(name);
    }

    @Override
    public boolean groupCheck(Group currentGroup) {
        if (currentGroup == null
                || currentGroup.getName() == null
                || currentGroup.getName().trim().equals("")) {
            // groupname not defined;
            return false;
        }
        Group group = groupDao.findByName(currentGroup.getName());
        if (group == null || group.getId().equals(currentGroup.getId())) {
            // groupname is new or not changed
            return true;
        }
        // groupname used
        return false;
    }

    @Override
    public Group getDefaultGroup() {
        return groupDao.findGroupByName(defaultGroupName);
    }

    @Override
    public List<Group> getGroupsByUserId(long id) {
        return groupDao.findGroupsByUserId(id);
    }

    @Override
    public List<Long> getRolesIds(long id) {
        return groupDao.findRolesIds(id);
    }

    @Override
    public List<Role> findAllRolesByUserId(final long userId) {
        return roleDao.findAllRolesByUserId(userId);
    }

    @Override
    public List<String> findAllGrantsByUserId() {
        final Long userId = AuthenticationUtils.getAuthentication().getId();
        final List<Role> roleList = groupService.findAllRolesByUserId(userId);
        final List<String> result = new ArrayList<>(roleList.size());
        for (final Role role : roleList) {
            result.add(role.getName());
        }
        return result;
    }
}
