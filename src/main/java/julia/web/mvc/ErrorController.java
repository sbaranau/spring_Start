package julia.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Nikolai_Tarasevich
 */
@Controller
@RequestMapping("/error.html")
public class ErrorController {

    /**
     * @param status status
     * @param model model
     * @param request request
     * @return fillErrorPage(status, model, request)
     */
    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam("errorCode") String status,
            Model model, HttpServletRequest request) {
        return fillErrorPage(status, model, request);
    }

    /**
     * @param status status
     * @param model model
     * @param request request
     * @return fillErrorPage(status, model, request)
     */
    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam("errorCode") String status,
            Model model, HttpServletRequest request) {
        return fillErrorPage(status, model, request);
    }

    /**
     * @param status status
     * @param model model
     * @param request request
     * @return error
     */
    private String fillErrorPage(String status, Model model,
            HttpServletRequest request) {
        Exception exception =
                (Exception) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            request.setAttribute("exceptionMessage", exception.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            request.setAttribute("stackTrace", sw.toString());
        }
        model.addAttribute("status", status);
        return ".error";
    }

}
