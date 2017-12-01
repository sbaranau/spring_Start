package julia.web.mvc.logging;

import julia.dto.log.LogDataDTO;
import julia.enums.AvailableLogFilesEnum;
import julia.exception.JuliaApiException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


@RestController
@RequestMapping(value = "/logging/data_download_log", produces = "application/json")
public class DataDownloadLogController {

    @Value("${logs.path}")
    private String path;

    /**
     * Найти логи о проведенных загрузках данных"
     * @return список путей на логи о проведенных загрузках данных
     */
    @RequestMapping(value = "/data.json", method = RequestMethod.GET)
    public ArrayList<LogDataDTO> getLogs() {
        File[] folderList;
        ArrayList<LogDataDTO> logList = new ArrayList<>();
        try {
            File folder = new File(path);
            if (folder.exists()) {
                folderList = folder.listFiles();
                for (File file : folderList) {
                    for (AvailableLogFilesEnum filesEnum : AvailableLogFilesEnum.values()) {
                        if (file.getName().contains(filesEnum.getFileName())) {
                            String name = file.getName();
                            BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                            Date date = new Date(basicFileAttributes.lastModifiedTime().toMillis());
                            LogDataDTO log = new LogDataDTO(date, name);
                            logList.add(log);
                        }
                    }
                }
            }
            logList.sort(LogDataDTO.compareByDate);
            return logList;
        }catch (final IOException e) {
                throw new JuliaApiException("Ошибка чтения файлов");
        }
     }
    /**
     * Получить log файл по URL
     */
    @RequestMapping(value = "/download_log_file.json")
    public void getPluginInstruction(@RequestParam final String fileName, final HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-type", "application/txt");
            IOUtils.copy(new FileInputStream(org.apache.commons.io.FileUtils.getFile(path, fileName)), response.getOutputStream());
            response.flushBuffer();
        } catch (final IOException e) {
            throw new JuliaApiException("Не удалось загрузить файл");
        }
    }
}