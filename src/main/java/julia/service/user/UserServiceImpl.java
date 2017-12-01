package julia.service.user;

import julia.dao.GroupDao;
import julia.dao.UserDao;
import julia.dto.SimpleObjectDTO;
import julia.dto.users.UserDTO;
import julia.entity.Group;
import julia.entity.NamedEntity;
import julia.entity.SecurityUser;
import julia.entity.User;
import julia.enums.GroupEnum;
import julia.enums.SettingsEnum;
import julia.security.utils.AuthenticationUtils;
import julia.service.data.DataService;
import julia.web.mvc.SearchUsersFilter;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static julia.web.mvc.Validator.isImageFile;


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
    private GroupDao groupDao;

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

        user.setPhoto(checkUserPhoto(user, photo, filename));

        save(user);

        groupDao.deleteUsersGroups(user.getId());

        for (Long groupId : groups) {
            groupDao.setUsersGroups(user.getId(), groupId);
        }
    }

    /**
     * Изменить фото пользователя если существует
     *
     * @param user  пользователь
     * @param photo фото пользователя
     * @param filename наименование файла
     */
    private Long checkUserPhoto(final User user, final MultipartFile photo, final String filename) {
        if (filename == null && user.getPhoto() != null) {
            user.setPhoto(null);
            userDao.update(user);
            dataService.deleteData(user.getPhoto());
            return null;
        } else if (photo != null && !photo.isEmpty() && isImageFile(photo)) {
            return dataService.convertMultipartFile(photo, user.getPhoto());
        }
        return user.getPhoto();
    }

    @Override
    public User get(long id) {
        return userDao.read(id);
    }

    @Override
    public List<User> getAllWithoutFired() {
        return userDao.findAllWithoutFired();
    }

    @Override
    public List<User> getAllWithoutFired(long groupId) {
        return userDao.findAllWithoutFiredWithGroup(groupId);
    }

    @Override
    public List<User> getAllWithoutFiredExcept(long id) {
        List<User> users = userDao.findAllWithoutFired();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                // предполагается, что в списке только один пользователь с таким
                // id
                iterator.remove();
                break;
            }
        }

        return users;
    }

    @Override
    public List<Long> getGroupsIds(long id) {
        return userDao.findGroupsIds(id);
    }

    @Override
    public boolean loginExists(String login) {
        return userDao.loginExists(login);
    }

    @Override
    public void delete(Long id) {
        groupDao.deleteUsersGroups(id);
        userDao.removeSensors(id);
        userDao.delete(id);
    }

    @Override
    public List<Group> getGroups(Long uid) {
        return groupDao.findGroupsByUserId(uid);
    }

    @Override
    public void setUserGroups(Long uid, List<Group> groups) {
        groupDao.deleteUsersGroups(uid);
        for (Group g : groups) {
            groupDao.setUsersGroups(uid, g.getId());
        }
    }


    @Override
    public void changePasswordCurrentUser(String password) {
        String encodePassword = passwordEncoder.encode(password);
        SecurityUser securityUser = AuthenticationUtils.getAuthentication();
        securityUser.setPassword(encodePassword);
        User user = get(securityUser.getId());
        user.setPassword(encodePassword);
        save(user);
    }

    @Override
    public List<User> findByGroup(GroupEnum... groups) {
        List<GroupEnum> grs = new ArrayList<>();
        Collections.addAll(grs, groups);
        return userDao.findByGroup(grs);
    }

    @Override
    public void setFired(long id, boolean fired) {
        Boolean currentFired = false;
        if (userDao.read(id).getStatusId() == '3') {
            currentFired = true;
        }
        // Если указаный сотрудник был уволен, а теперь восстановлен,
        // то пароль изменяется на пароль по умолчанию
        if (currentFired != null && currentFired && !fired) {
            String defaultPassword = settings.get(SettingsEnum.COMMON_PASSWORD_DEFAULT);
            String encodedDefaultPassword = passwordEncoder.encode(defaultPassword);
            userDao.changePassword(id, encodedDefaultPassword);
        }

        if (fired) {
            userDao.changeStatus(id, 3);
        } else {
            userDao.changeStatus(id, 1);
        }
    }

    @Override
    public User getOKKHead() {
        List<GroupEnum> groups = new ArrayList<>();
        groups.add(GroupEnum.HEAD_OKK);
        List<User> users = userDao.findByGroup(groups);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User getByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User getUserDepartmentByUserId(long userId) {
        return userDao.findUserDepartmentByUserId(userId);
    }

    @Override
    public List<User> findAllByDepartmentId(final Long departmentId) {
        return userDao.findAllByDepartmentId(departmentId);
    }

    @Override
    public List<User> getUserListByDepartmentId(final long departmentId) {
        return userDao.findAllByDepartmentId(departmentId);
    }

    @Override
    public List<User> getActiveUserListByDepartmentId(final long departmentId) {
        return userDao.findActiveByDepartmentId(departmentId);
    }

    @Override
    public List<NamedEntity> getUsersByDepartmentId(long departmentId) {
        List<NamedEntity> users = new ArrayList<>();
        for (User user : getUserListByDepartmentId(departmentId)) {
            NamedEntity userShot = new NamedEntity();
            userShot.setName(user.getFullNameWithInitials());
            userShot.setId(user.getId());
            users.add(userShot);
        }
        return users;
    }

    @Override
    public List<NamedEntity> findUsersByPostIdAndDate(long postId, Date date) {
        List<NamedEntity> users = new ArrayList<>();
        for (User user : userDao.findByPostIdAndDate(postId, date)) {
            NamedEntity userShort = new NamedEntity();
            userShort.setName(user.getFullNameWithInitials());
            userShort.setId(user.getId());
            users.add(userShort);
        }
        return users;
    }

    @Override
    public List<NamedEntity> getUserByDepartmentIdAndReviewId(long departmentId, long reviewId) {
        List<NamedEntity> users = new ArrayList<>();
        for (User user : userDao.findUserByDepartmentIdAndReviewId(departmentId, reviewId)) {
            NamedEntity userShot = new NamedEntity();
            userShot.setName(user.getFullNameWithInitials());
            userShot.setId(user.getId());
            users.add(userShot);
        }
        return users;
    }

    @Override
    public List<User> getUserByDepartmentIdAndPost(long departmentId, List<Long> postIds) {
        return userDao.findUserByDepartmentIdAndPost(departmentId, postIds);
    }
    @Override
    public boolean checkContainsGroup(Long userId, Long groupId) {
        boolean result = false;

        for (Group grp : getGroups(userId)) {
            if (grp.getId().equals(groupId)) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Long getCurrentUserDepartment(Long userId) {

        return userDao.getCurrentUserDepartment(userId);

    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getUsersByFullNameAndDateBirth(String name, String surname, String middleName, Date dateBirth) {
        return userDao.findByFullNameAndDateBirth(name, surname, middleName, dateBirth);
    }

    @Override
    public List<User> getUsersByDepartmentOrPost(Long departmentId, Long postId) {
        return userDao.findUsersByDepartmentOrPost(departmentId, postId);
    }

    @Override
    public void changeUserStatus(final Long userId, final Long statusId) {
        userDao.changeStatus(userId, statusId);
    }

    @Override
    public List<UserDTO> findAllUserDTO(final SearchUsersFilter filter) {
        return userDao.findAllUserDTO(filter);
    }

    @Override
    public TreeList<SimpleObjectDTO> convertToSimpleObjectDTOTreeList(final List<UserDTO> users) {
        final TreeMap<String, List<UserDTO>> departmentUserListMap = new TreeMap<>();
        for (final UserDTO user : users) {
            if (!departmentUserListMap.containsKey(user.getDepartmentName())) {
                final List<UserDTO> userDTOList = new ArrayList<>();
                userDTOList.add(user);
                departmentUserListMap.put(user.getDepartmentName(), userDTOList);
            } else {
                departmentUserListMap.get(user.getDepartmentName()).add(user);
            }
        }
        return departmentUserListMap.entrySet().stream()
                .map(e -> new SimpleObjectDTO<>(e.getKey(), e.getValue()))
                .collect(Collectors.toCollection(TreeList::new));
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
    public void updateUserByUserDto(final User userFromUserForm, final Long userId, final List<Long> groups, final MultipartFile file, final String fileName) {
        //TODO change this, it's temporary solution because i don't have a time to fix
        final User user = get(userId);
        user.setName(userFromUserForm.getName());
        user.setMiddleName(userFromUserForm.getMiddleName());
        user.setSurname(userFromUserForm.getSurname());
        user.setDateBirth(userFromUserForm.getDateBirth());
        user.setPhone(userFromUserForm.getPhone());
        user.setEmail(userFromUserForm.getEmail());
        user.setChief(userFromUserForm.isChief());
        user.setLogin(userFromUserForm.getLogin());
        user.setPhoto(checkUserPhoto(user, file, fileName));
        userDao.update(user);

        groupDao.deleteUsersGroups(user.getId());
        for (Long groupId : groups) {
            groupDao.setUsersGroups(user.getId(), groupId);
        }
    }
}
