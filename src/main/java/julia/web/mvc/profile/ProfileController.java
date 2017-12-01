package julia.web.mvc.profile;

import julia.entity.User;
import julia.exception.JuliaApiException;
import julia.security.utils.AuthenticationUtils;
import julia.service.group.GroupService;
import julia.service.user.UserService;
import julia.web.mvc.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Контроллер для личного кабинета.
 * @author Artiom_Buevich
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/settings/password.json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
	ResponseForm saveChangePassword(@RequestBody final PasswordForm passwordForm) {
        User user = userService.get(AuthenticationUtils.getAuthentication().getId());
        if (!passwordEncoder.matches(passwordForm.getPassword(),user.getPassword())){
                throw new JuliaApiException("Ошибка ввода действующего пароля");
        }
        else
        if(!passwordForm.getNewPassword().equals(passwordForm.getConfirm())) {
            throw new JuliaApiException("Поля <Новый пароль> и <Подтверждение> имеют разные значения");
        }else{
            user.setDefaultPassword(false);
            user.setPassword(passwordEncoder.encode(passwordForm.getNewPassword()));
            userService.save(user);
        }
        return new ResponseForm();
    }
}
