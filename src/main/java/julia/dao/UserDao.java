package julia.dao;

import julia.dto.users.UserDTO;
import julia.entity.User;
import julia.enums.GroupEnum;
import julia.web.mvc.SearchUsersFilter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Ivan Shyrma
 * @author Aleksandr Golovnyov
 * @author Stanislav Bulavskiy
 */
public interface UserDao extends Dao<User, Long> {

    List<User> findAllWithoutFired();

    List<User> findAllWithoutFiredWithGroup(long groupId);

    List<Long> findGroupsIds(long id);

    void removeSensors(long id);

    boolean loginExists(String login);

    Long getCurrentUserDepartment(Long userId);

    /**
     * Получаем список пользователей относящихся к определённым группам
     * @param groups список групп
     * @return список
     */
    List<User> findByGroup(@Param("groups") List<GroupEnum> groups);

    void changePassword(@Param("id") long id, @Param("password") String encodedPassword);

    User findByPhone(String phone);

    User findByEmail(String email);

    List<User> findByStatusId(long statusId);

    void changeStatus(@Param("id") long id, @Param("statusId") long statusId);

    User findUserDepartmentByUserId(@Param("id") long id);

    List<User> findAllByDepartmentId(@Param("departmentId") long departmentId);

    List<User> findActiveByDepartmentId(@Param("departmentId") long departmentId);

    /**
     * Получаем список пользователей, которые до <code>date</code> занимали должность <code>postId</code>
     * @param postId идентификатор должности
     * @param date дата
     * @return список пользователей
     */
    List<User> findByPostIdAndDate(@Param("postId") Long postId, @Param("date") Date date);

    List<User> findUserByDepartmentIdAndReviewId(@Param("departmentId") long departmentId, @Param("reviewId") long reviewId);

    /**
     * Возвращает список пользователей по отделу и должностям
     * @param departmentId идентификатор отдела
     * @param postIds список должностей
     * @return список пользователей
     */
    List<User> findUserByDepartmentIdAndPost(@Param("departmentId") long departmentId, @Param("postIds") List<Long> postIds);

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
     * Найти пользователей по подразделению или должности
     * @param departmentId идентификатор подразделения
     * @param postId идентификатор должности
     * @return пользователи
     */
    List<User> findUsersByDepartmentOrPost(@Param("departmentId") Long departmentId, @Param("postId") Long postId);

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
}
