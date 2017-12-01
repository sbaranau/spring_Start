package julia.web.mvc.upload;

import julia.dto.SimpleValueDTO;
import julia.entity.Data;
import julia.service.data.DataService;
import julia.web.mvc.ControllerConstants;
import julia.web.mvc.ResponseForm;
import julia.web.utils.WebUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер для выгрузки данных.
 *
 * @author Artiom_Buevich
 *
 */
@RestController
public class UploadDataController {

    @Autowired
    private DataService dataService;

    private static final Logger LOG = LoggerFactory.getLogger(UploadDataController.class);

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public void getFile(@RequestParam(value = "id", required = true, defaultValue = "-1") Long fileId,
            HttpServletRequest request, HttpServletResponse response) {
        Data data = dataService.getDataById(fileId);
        if (data == null) {
            LOG.error("Не удалось найти файл с id = {}.Файл не существует.", fileId);
        } else {
            try {
                WebUtils.fillResponseByFile(data.getMimeType(), data.getName(), data.getData(), request, response);
            } catch (IOException e) {
                LOG.error("Не удалось выгрузить файл с id = {}", fileId, e);
            }
        }
    }

    @RequestMapping(value = "/resources", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseForm getPhoto(@RequestBody Long id) {
        ResponseForm form = new ResponseForm();
        try {
            Data data = dataService.getDataById(id);

            if (data == null) {
                LOG.error("Не удалось найти файл с id = {}.Файл не существует.", id);
            } else {
                form.setData(data);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            form.setResultCode(ControllerConstants.RESULT_SERVER_ERROR);
        }
        return form;
    }

    /**
     * Загрузить файл из ресурсов
     * @param file имя файла
     * @param request http-запрос
     * @param response http-ответ
     */
    @RequestMapping(value = "/resources/application", method = RequestMethod.GET)
    public void getResourceFile(@RequestParam("file") String file, HttpServletRequest request, HttpServletResponse response) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(file)) {
            if (is == null) {
                throw new FileNotFoundException("Файл \"" + file + "\" не найден");
            }
            WebUtils.fillResponseByFile(Files.probeContentType(Paths.get(file)), FilenameUtils.getName(file), IOUtils.toByteArray(is), request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отобразить файл в браузере
     * @param fileId идентификатор файла
     * @param request http-запрос
     * @param response http-ответ
     */
    @RequestMapping(value = "/resources/show", method = RequestMethod.GET)
    public void showFile(@RequestParam(value = "id", required = true, defaultValue = "-1") Long fileId,
                         HttpServletRequest request, HttpServletResponse response) {
        Data data = dataService.getDataById(fileId);
        if (data == null) {
            LOG.error("Не удалось найти файл с id = {}.Файл не существует.", fileId);
        } else {
            try {
                WebUtils.fillResponseByFileInline(data.getMimeType(), data.getName(), data.getData(), request, response);
            } catch (IOException e) {
                LOG.error("Не удалось выгрузить файл с id = {}", fileId, e);
            }
        }
    }

    /**
     * полученик имени файла
     * @param fileId идентификатор файла
     */
    @RequestMapping(value = "/resources/{id}/name.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, String>> showName(@PathVariable("id") Long fileId) {
        Data data = dataService.getDataById(fileId);
        Map<String, String> name = new HashMap<>();
        String nameValue = "";
        if (data == null) {
            LOG.error("Не удалось найти файл с id = {}.Файл не существует.", fileId);
            nameValue = String.format("Не удалось найти файл с id = %d .Файл не существует.", fileId);
        } else {
            nameValue = data.getName();
        }
        name.put("dataName", nameValue);
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    @RequestMapping(value = "resources/upload_file.json", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
    public SimpleValueDTO<Long> uploadFile(@RequestParam final MultipartFile file) {
        return new SimpleValueDTO<>(dataService.convertMultipartFile(file));
    }

    @RequestMapping(value = "resources/delete_file.json", method = RequestMethod.DELETE)
    public SimpleValueDTO<Long> deleteFile(@RequestParam final long fileId) {
        dataService.deleteData(fileId);
        return new SimpleValueDTO<>(fileId);
    }
}
