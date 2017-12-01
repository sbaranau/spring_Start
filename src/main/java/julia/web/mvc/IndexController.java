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

    /**
     * Управление трудовыми ресурсами
     * 
     * @return представление
     */
    @RequestMapping("/workforce.html")
    public String getWorkforceMenu() {
        return ".workforce";
    }

    /**
     * Мониторинг перепадов давления
     * 
     * @return представление
     */
    @RequestMapping("/pressure.html")
    public String getPressureMenu() {
        return ".pressure";
    }

    /**
     * Управление производством
     * 
     * @return представление
     */
    @RequestMapping("/production.html")
    public String getProductionMenu() {
        return ".production";
    }
}
