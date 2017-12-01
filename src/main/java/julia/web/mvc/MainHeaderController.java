package julia.web.mvc;

import julia.dto.MainHeaderDTO;
import julia.entity.SecurityUser;
import julia.enums.SettingsEnum;
import julia.security.utils.AuthenticationUtils;
import julia.service.settings.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Котроллер работы с хеадером приложения
 *
 * @author Pudul Yuriy
 */
@Controller
@RequestMapping(value = "/main/header", produces = "application/json")
public class MainHeaderController {

    @Autowired
    private SystemSettingsService systemSettingsService;

    /**
     * Основные данные в хеадере
     *
     * @return {@link MainHeaderDTO}
     */
    @RequestMapping(value = "/header.json", method = RequestMethod.GET)
    public ResponseEntity<MainHeaderDTO> getData() {
        final String organizationName = systemSettingsService.getValues().get(SettingsEnum.COMMON_ORGANIZATION_NAME.getKey());
        final SecurityUser authenticationData = AuthenticationUtils.getAuthentication();

        final MainHeaderDTO result = new MainHeaderDTO();
        result.setOrganization(organizationName);
        result.setUserName(authenticationData.getUsername());
        result.setUserId(authenticationData.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
