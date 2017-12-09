package julia.web.mvc.settings;

import julia.dto.settings.CommonSettingsDTO;
import julia.dto.users.UserDTO;
import julia.security.utils.AuthenticationUtils;
import julia.service.settings.SystemSettingsService;
import julia.service.user.UserService;
import julia.web.mvc.SearchUsersFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author sergey
 */
@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SystemSettingsService systemSettingsService;

    @Autowired
    private UserService userService;


    /**
     * Сохраняет изменения настроек
     * @param settingList список настроек
     * @return список сохранённых настроек
     */
    @RequestMapping(value = "/common/save.json", method = RequestMethod.POST, produces = "application/json")
    public List<CommonSettingsDTO> saveCommonSettings(@RequestBody final List<CommonSettingsDTO> settingList) {
        systemSettingsService.updateValues(settingList);
        return settingList;
    }

    /**
     * Получить список настроек
     * @return список настроек
     */
    @RequestMapping(value = "/common/list.json", method = RequestMethod.GET, produces = "application/json")
    public List<CommonSettingsDTO> getCommonSettings() {
        return CommonSettingsDTO.getCommonSettingsDTO(systemSettingsService.getValues());
    }

    /**
     * Проверка авторизации пользователя и определение всех ролей для пользователя
     * @return список всех ролей пользователя
     */
    @RequestMapping(value = "/is_auth.json", method = RequestMethod.GET, produces = "application/json")
    public List<String> isAuth() {
        try {
            final Long userId = AuthenticationUtils.getAuthentication().getId();
            return userService.findAllRolesByUserId(userId);
        } catch (final Exception ex) {
            return Collections.<String>emptyList();
        }
    }

    /**
     * Получить данные текущего залогиненного пользователя
     *
     * @return залогиненный пользователь
     */
    @RequestMapping(value = "/current_user.json", method = RequestMethod.GET, produces = "application/json")
    public UserDTO getCurrentUser() {
        final Long userId = AuthenticationUtils.getAuthentication().getId();
        return userService.getUserDTO(userId);
    }
    /**
     * Получить список пользователей
     *
     * @return список пользователей
     */
    @RequestMapping(value = "/users/all_users.json", method = RequestMethod.POST, produces = "application/json")
    public List<UserDTO> getAllUsersWithoutFilters() {
        return userService.findAllUserDTO(new SearchUsersFilter(true));
    }

}
