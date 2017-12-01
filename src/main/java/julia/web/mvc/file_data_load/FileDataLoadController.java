package julia.web.mvc.file_data_load;

import julia.service.file_data_load.FileDataLoadService;
import julia.web.mvc.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Контроллер для работы с загрузкой данных из файлов
 * @author vandronov
 */
@Controller
@RequestMapping("/fileDataLoad")
public class FileDataLoadController {

    @Autowired
    private FileDataLoadService fileDataLoadService;

    /**
     * Загрузить все типы данных
     * @return форма-ответ
     */
    @RequestMapping(value = "/loadAll.html", method = RequestMethod.GET)
    public @ResponseBody
    ResponseForm loadAll() {
        fileDataLoadService.loadAll();
        return new ResponseForm();
    }

}
