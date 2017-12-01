package julia.web.mvc.settings;

import julia.dto.settings.CommonSettingsDTO;
import julia.dto.users.UserDTO;
import julia.enums.SettingsEnum;
import julia.security.utils.AuthenticationUtils;
import julia.service.group.GroupService;
import julia.service.settings.SystemSettingsService;
import julia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static julia.enums.SettingsEnum.*;

/**
 * @author Aleksandr Golovnyov
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SystemSettingsService systemSettingsService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @RequestMapping
    public String settings(Model model) {
        return ".settings";
    }

    @RequestMapping("/common.html")
    public String common(SettingsForm form) {

        form.setSettings(systemSettingsService.getValues());

        return ".settings.common";
    }

    @RequestMapping("/microclimateMap.html")
    public String general(SettingsForm form) {

        form.setSettings(systemSettingsService.getValues());

        return ".settings.microclimateMap";
    }

    @RequestMapping("/mail.html")
    public String mail(SettingsForm form) {

        form.setSettings(systemSettingsService.getValues());

        return ".settings.mail";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(SettingsForm form, BindingResult result) {

        systemSettingsService.updateValues(form.getSettings());

        return "redirect:/settings.html";
    }

    /**
     * Перейти на страницу с настройками загрузки данных из файлов
     * @param form форма с настройками
     * @return имя страницы для отображения
     */
    @RequestMapping("/fileDataLoad.html")
    public String fileDataLoad(SettingsForm form) {
        form.setSettings(systemSettingsService.getValues());
        return ".settings.fileDataLoad";
    }

    /**
     * Сохраняет изменения настроек
     * @param settingList список настроек
     * @return список сохранённых настроек
     */
    @RequestMapping(value = "/common/save.json", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List<CommonSettingsDTO>> saveCommonSettings(@RequestBody final List<CommonSettingsDTO> settingList) {
        systemSettingsService.updateValues(settingList);
        return new ResponseEntity<>(settingList, HttpStatus.OK);
    }

    /**
     * Получить список настроек
     * @return список настроек
     */
    @RequestMapping(value = "/common/list.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CommonSettingsDTO>> getCommonSettings() {
        final List<SettingsEnum> values = Arrays.asList(COMMON_ORGANIZATION_NAME, COMMON_PASSWORD_DEFAULT, COMMON_FILE_MAXSIZE);
        return new ResponseEntity<>(CommonSettingsDTO.getCommonSettingsDTO(systemSettingsService.getValues(values)), HttpStatus.OK);
    }

    /**
     * Проверка авторизации пользователя и определение всех ролей для пользователя
     * @return список всех ролей пользователя
     */
    @RequestMapping(value = "/is_auth.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<String>> isAuth() {
        try {
            return new ResponseEntity<>(groupService.findAllGrantsByUserId(), HttpStatus.OK);
        } catch (final Exception ex) {
            return new ResponseEntity<>(Collections.<String>emptyList(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Получить данные текущего залогиненного пользователя
     *
     * @return залогиненный пользователь
     */
    @RequestMapping(value = "/current_user.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UserDTO> getCurrentUser() {
        final Long userId = AuthenticationUtils.getAuthentication().getId();
        return new ResponseEntity<>(userService.getUserDTO(userId), HttpStatus.OK);
    }

}
