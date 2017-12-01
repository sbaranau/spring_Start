package julia.web.utils;

import julia.entity.FileContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Вспомогательный класс для работы с web
 * @author vandronov
 */
public class WebUtils {

    private WebUtils() {
    }

    /**
     * Заполнить http-ответ файлом с содержимым
     * @param fileContent файл с содержимым
     * @param request http-запрос
     * @param response http-ответ
     * @throws java.io.IOException ошибка ввода-вывода
     */
    public static void fillResponseByFile(FileContent fileContent, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fillResponseByFile(fileContent.getMimeType(), fileContent.getName(), fileContent.getContent(), request, response);
    }

    /**
     * Заполнить http-ответ файлом с содержимым
     * @param mimeType тип содержимого файла
     * @param name имя файла
     * @param content содержимое файла
     * @param request http-запрос
     * @param response http-ответ
     * @throws java.io.IOException ошибка ввода-вывода
     */
    public static void fillResponseByFile(String mimeType, String name, byte[] content, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(mimeType);
        String userAgent = request.getHeader("User-Agent");
        String fileNameEncoded = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
        if (userAgent != null && userAgent.contains("Firefox")) {
            response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + fileNameEncoded);
        } else {
            response.setHeader("Content-disposition", "attachment; filename=" + fileNameEncoded);
        }
        response.getOutputStream().write(content);
    }

    /**
     * Сформировать ответ со встроенным файлом
     * @param fileContent файл с содержимым
     * @param request http-запрос
     * @return ответ со встроенным файлом
     * @throws IOException ошибка ввода-вывода
     */
    public static ResponseEntity<byte[]> createResponseWithFileInline(FileContent fileContent, HttpServletRequest request) throws IOException {
        String userAgent = request.getHeader("User-Agent");
        String fileNameEncoded = URLEncoder.encode(fileContent.getName(), "UTF-8").replace("+", "%20");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileContent.getMimeType()));
        if (userAgent != null && userAgent.contains("Firefox")) {
            headers.add("content-disposition", "inline;filename*=UTF-8''" + fileNameEncoded);
        } else {
            headers.add("content-disposition", "inline;filename=" + fileNameEncoded);
        }
        return new ResponseEntity<>(fileContent.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Заполнить http-ответ встроенным файлом с содержимым
     * @param mimeType тип содержимого файла
     * @param name имя файла
     * @param content содержимое файла
     * @param request http-запрос
     * @param response http-ответ
     * @throws java.io.IOException ошибка ввода-вывода
     */
    public static void fillResponseByFileInline(String mimeType, String name, byte[] content,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(mimeType);
        String userAgent = request.getHeader("User-Agent");
        String fileNameEncoded = URLEncoder.encode(name, "UTF-8").replace("+", "%20");
        if (userAgent != null && userAgent.contains("Firefox")) {
            response.setHeader("content-disposition", "inline;filename*=UTF-8''" + fileNameEncoded);
        } else {
            response.setHeader("content-disposition", "inline;filename=" + fileNameEncoded);
        }
        response.getOutputStream().write(content);
    }

}
