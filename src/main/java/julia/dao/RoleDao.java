package julia.dao;

import julia.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ivan Shyrma
 * @author Aleksandr Golovnyov
 * @author Nikolai Tarasevich
 */
public interface RoleDao extends Dao<Role, Long> {

    List<Role> findGroupsRoles(Long gid);

    void setGroupsRoles(@Param("gid") Long gid, @Param("rid") Long rid);

    void deleteGroupsRoles(Long gid);

    /**
     * Получить список ролей для пользователя
     * @param userId id пользователя
     * @return список ролей
     */
    List<Role> findAllRolesByUserId(final Long userId);
}
