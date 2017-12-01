package julia.dao;

import julia.entity.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Nikolai_Tarasevich
 */
public interface GroupDao extends Dao<Group, Long> {

    Group findByName(String username);

    List<Group> findUsersGroups(Long uid);

    List<Group> findGroupsByUserId(Long id);

    void setUsersGroups(@Param("uid") Long uid, @Param("gid") Long gid);

    void deleteUsersGroups(Long uid);

    Long getMaxId();

    Group findGroupByName(String name);

    List<Long> findRolesIds(long id);
}
