package julia.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for login and main pages
 * 
 * @author Ivan Shyrma
 * @author Aleksandr Golovnyov
 */
@Controller
public class IndexController {

    /**
     * Main page
     * 
     * @return view name of main page
     */
    @RequestMapping("/index.html")
    public String index(Model model) {
        return ".index";
    }

    /**
     * Login page
     * 
     * @return view name of the login page
     */
    @RequestMapping("/login.html")
    public String login() {
        return ".login";
    }

}
