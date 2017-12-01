package julia.service.user;

import julia.dto.SimpleObjectDTO;
import julia.dto.users.UserDTO;
import julia.entity.Group;
import julia.entity.NamedEntity;
import julia.entity.User;
import julia.enums.GroupEnum;
import julia.web.mvc.SearchUsersFilter;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface UserService {

    void save(User user);

    void save(User user, List<Long> groups, MultipartFile photo, String filename);

    User get(long id);

    List<User> getAllWithoutFired();

    List<User> getAllWithoutFired(long groupId);

    List<User> getAllWithoutFiredExcept(long id);


    List<Long> getGroupsIds(long id);

    boolean loginExists(String login);

    void delete(Long id);

    List<Group> getGroups(Long uid);

    void setUserGroups(Long uid, List<Group> groups);

    void changePasswordCurrentUser(String password);
    
    boolean checkContainsGroup(Long userId,Long groupId);
    
    Long getCurrentUserDepartment(Long userId);

    /**
     * Получаем список пользователей относящихся к определённым группам
     * 
     * @param groups
     *            список групп
     * @return список
     */
    List<User> findByGroup(GroupEnum... groups);

    /**
     * Помечает сотрудника как уволенный или снимает пометку
     * 
     * @param id
     *            идентификатор пользователя
     * @param fired
     *            значение
     */
    void setFired(long id, boolean fired);

    /**
     * Получает одного из пользователей, находящего в группе "Начальник ОКК"
     * 
     * @return Класс {@link User}
     */
    User getOKKHead();

    User getByPhone(String phone);

    User getByEmail(String email);

    User getUserDepartmentByUserId(long userId);

    List<NamedEntity> getUsersByDepartmentId(long departmentId);

    /**
     * Получить список пользователей по отделу
     * @param departmentId id отдела
     * @return список пользователей
     */
    List<User> getUserListByDepartmentId(final long departmentId);

    /**
     * Получить список активных пользователей по отделу
     * @param departmentId id отдела
     * @return список активных пользователей
     */
    List<User> getActiveUserListByDepartmentId(final long departmentId);

    /**
     * Получить список пользователей по id отдела
     * @param departmentId id отдела
     * @return список пользователей
     */
    List<User> findAllByDepartmentId(final Long departmentId);

    /**
     * Получаем список пользователей, последняя должность которых {@code postId}
     * до момента {@code date} включительно. Используется при добавлении информации
     * об обучении сотрудников в старых документах, добавленных через журнал.
     *
     * @param postId идентификатор должности
     * @param date дата
     * @return список
     */
    List<NamedEntity> findUsersByPostIdAndDate(long postId, Date date);

    List<NamedEntity> getUserByDepartmentIdAndReviewId(long departmentId, long reviewId);

    /**
     * Возвращает список пользователей по отделу и должностям
     * @param departmentId идентификатор отдела
     * @param postIds список должностей
     * @return список пользователей
     */
    List<User> getUserByDepartmentIdAndPost(long departmentId, List<Long> postIds);

    /**
     * Найти всех пользователей
     * @return все пользователи
     */
    List<User> getAllUsers();

    /**
     * Получить пользователей по ФИО и дате рождения
     * @param name имя
     * @param surname фамилия
     * @param middleName отчество
     * @param dateBirth дата рождения
     * @return пользователи
     */
    List<User> getUsersByFullNameAndDateBirth(String name, String surname, String middleName, Date dateBirth);


    /**
     * Получить пользователей по подразделению или должности
     * @param departmentId идентификатор подразделения
     * @param postId идентификатор должности
     * @return пользователи
     */
    List<User> getUsersByDepartmentOrPost(Long departmentId, Long postId);

    /**
     * Меняет статус пользователя
     * @param userId id пользователя
     * @param statusId id статуса пользователя
     */
    void changeUserStatus(final Long userId, final Long statusId);

    /**
     * Получить список пользователей с учётом фильтра
     * @param filter фильтр
     * @return список пользователей {@link UserDTO}
     */
    List<UserDTO> findAllUserDTO(final SearchUsersFilter filter);

    /**
     * Конвертирует в список объектов {@link SimpleObjectDTO}
     * @param users список объектов {@link UserDTO}
     * @return упорядоченный список {@link SimpleObjectDTO}
     */
    TreeList<SimpleObjectDTO> convertToSimpleObjectDTOTreeList(final List<UserDTO> users);

    /**
     * Получить полное имя пользователя с инициалами
     * @param userId id пользователя
     * @return полное имя с инициалами или null, если нет такого пользователя
     */
    String getUserNameByUserId(final Long userId);

    /**
     * Получить DTO пользователя по id
     *
     * @param userId id пользователя
     * @return DTO пользователя
     */
    UserDTO getUserDTO(final Long userId);

    /**
     * Временное решение, апдейт пользователя блоки общая информация и доступы
     *
     * @param userFromUserForm объект пользователя для сохранения
     * @param userId id пользователя
     * @param groups группы(доступы) пользователя
     * @param file фото пользователя
     * @param fileName наименование файла фото
     */
    void updateUserByUserDto(final User userFromUserForm, final Long userId, final List<Long> groups, final MultipartFile file, final String fileName);
}
