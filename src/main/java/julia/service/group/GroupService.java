package julia.service.group;

import julia.entity.Group;
import julia.entity.Role;

import java.util.List;

/**
 * @author Nikolai_Tarasevich
 *
 */
public interface GroupService {

    Group getGroup(Long id);

    List<Group> getGroups();

    void saveGroup(Group group);

    void deleteGroup(Long gid);

    Role getRole(Long gid);

    List<Role> getRoles();

    List<Role> getRoles(Long gid);

    List<Group> getGroupsByUserId(long id);

    Group getDefaultGroup();

    void setGroupRoles(Long uid, List<Role> roles);

    void setGroupRoleIds(Long uid, List<Long> roleIds);

    Group findGroupByName(String name);

    /**
     * проверяет, чтобы имя группы было не пустое и не занято другим
     * @return пройдена ли проверка
     */
    boolean groupCheck(Group currentGroup);

    List<Long> getRolesIds(long id);

    /**
     * Получить список ролей для пользователя
     * @param userId id пользователя
     * @return список ролей
     */
    List<Role> findAllRolesByUserId(final long userId);

    /**
     * Получить список грантов(наименование роли)
     * @return список наименований ролей
     */
     List<String> findAllGrantsByUserId();
}
