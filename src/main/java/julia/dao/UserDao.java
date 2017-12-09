package julia.dao;

import julia.dto.users.UserDTO;
import julia.entity.User;
import julia.enums.GroupEnum;
import julia.web.mvc.SearchUsersFilter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author sergey
 */
public interface UserDao extends Dao<User, Long> {

    List<Long> findGroupsIds(long id);

    boolean loginExists(String login);

    void changePassword(@Param("id") long id, @Param("password") String encodedPassword);

    User findByPhone(String phone);

    User findByEmail(String email);


    /**
     * Найти пользователей по ФИО и дате рождения
     * @param name имя
     * @param surname фамилия
     * @param middleName отчество
     * @param dateBirth дата рождения
     * @return пользователи
     */
    List<User> findByFullNameAndDateBirth(@Param("name") String name, @Param("surname") String surname, @Param("middleName") String middleName,
            @Param("dateBirth") Date dateBirth);

    /**
     * Получить список пользователей с учётом фильтра
     * @param filter фильтр
     * @return список пользователей {@link UserDTO}
     */
    List<UserDTO> findAllUserDTO(final SearchUsersFilter filter);

    /**
     * Получить DTO пользователя по id
     *
     * @param userId id пользователя
     * @return DTO пользователя
     */
    UserDTO findUserDTO(final Long userId);

    List<String> findAllRolesByUserId(Long userId);
}
