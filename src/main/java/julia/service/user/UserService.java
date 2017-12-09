package julia.service.user;

import julia.dto.users.UserDTO;
import julia.entity.User;
import julia.web.mvc.SearchUsersFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 */
public interface UserService {

    void save(User user);

    void save(User user, List<Long> groups, MultipartFile photo, String filename);

    User get(long id);


    void delete(Long id);

    /**
     * Найти всех пользователей
     * @return все пользователи
     */
    List<User> getAllUsers();


    /**
     * Получить список пользователей с учётом фильтра
     * @param filter фильтр
     * @return список пользователей {@link UserDTO}
     */
    List<UserDTO> findAllUserDTO(final SearchUsersFilter filter);

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

    List<String> findAllRolesByUserId(Long userId);
}
