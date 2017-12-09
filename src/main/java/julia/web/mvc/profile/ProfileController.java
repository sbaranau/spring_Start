package julia.web.mvc.profile;

import julia.entity.PasswordForm;
import julia.entity.User;
import julia.exception.JuliaApiException;
import julia.security.utils.AuthenticationUtils;
import julia.service.user.UserService;
import julia.web.mvc.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для личного кабинета.
 * @author sergey
 */
@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/settings/password.json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
	ResponseForm saveChangePassword(@RequestBody final PasswordForm passwordForm) {
        User user = userService.get(AuthenticationUtils.getAuthentication().getId());
        if (!passwordEncoder.matches(passwordForm.getOldPassword(), user.getPassword())){
                throw new JuliaApiException("Ошибка ввода действующего пароля");
        }
        else
        if(!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            throw new JuliaApiException("Поля <Новый пароль> и <Подтверждение> имеют разные значения");
        }else{
            user.setPassword(passwordEncoder.encode(passwordForm.getNewPassword()));
            userService.save(user);
        }
        return new ResponseForm();
    }
}
