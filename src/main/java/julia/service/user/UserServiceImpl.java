package julia.service.user;

import julia.dao.UserDao;
import julia.dto.users.UserDTO;
import julia.entity.User;
import julia.service.data.DataService;
import julia.web.mvc.SearchUsersFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataService dataService;


    @Resource
    @Qualifier("settings")
    private Map<String, String> settings;

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            userDao.create(user);
        } else {
            userDao.update(user);
        }
    }

    @Override
    public void save(User user, List<Long> groups, MultipartFile photo, String filename) {
        save(user);
    }


    @Override
    public User get(long id) {
        return userDao.read(id);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<UserDTO> findAllUserDTO(final SearchUsersFilter filter) {
        return userDao.findAllUserDTO(filter);
    }


    @Override
    public String getUserNameByUserId(final Long userId) {
        return Optional.ofNullable(get(userId))
                .map(User::getFullNameWithInitials)
                .orElse(null);
    }

    @Override
    public UserDTO getUserDTO(final Long userId) {
        return userDao.findUserDTO(userId);
    }

    @Override
    public List<String> findAllRolesByUserId(Long userId) {
        return userDao.findAllRolesByUserId(userId);
    }


}
