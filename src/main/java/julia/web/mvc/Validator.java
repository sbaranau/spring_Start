package julia.web.mvc;

import julia.exception.JuliaValidationException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * Общий для всех полей валидатор
 *
 * @author Pudul Yuriy
 */
@Component
public class Validator {

    private static final String FILE_ODT = "application/vnd.oasis.opendocument.text";
    private static final String EXTENSION_ODT = "odt";
    /**
     * MIME-тип загружаемых изображений
     */
    private static final String FILE_JPG = "image/jpeg";
    private static final String FILE_PNG = "image/png";
    private static final String FILE_GIF = "image/gif";
    /**
     * Разрешенные форматы загрузки изображений
     */
    private static final String EXTENSION_JPEG = "jpeg";
    private static final String EXTENSION_JPG = "jpg";
    private static final String EXTENSION_PNG = "png";
    private static final String EXTENSION_GIF = "gif";
    /**
     * Разрешенный размер загрузки изображений в байтах
     */
    private static final long imageSize = 2097152;
    /**
     * Проверяет на пустоту пришедшую строку
     * @param string строка
     * @return не нулевую строку
     */
    public static String trimToEmpty(final String string) {
        return StringUtils.trimToEmpty(string);
    }

    /**
     * Валидирует является ли файл файлом с расширение odt, если нет тогда ошибка валидации
     * @param file загружаемый файл
     * @return файл
     */
    public static MultipartFile isFileOdt(final MultipartFile file) {
        if (file != null) {
            if (!Objects.equals(file.getContentType(), FILE_ODT) || !Objects.equals(FilenameUtils.getExtension(file.getOriginalFilename()), EXTENSION_ODT)) {
                throw new JuliaValidationException("Допустимые форматы для загрузки файлов: .odt");
            }
        }
        return file;
    }

    /**
     * Проверяет является ли входящий file изображением нужного формата и размера
     * @param file загружаемый файл
     * @return boolean значение
     */
    public static boolean isImageFile(final MultipartFile file){
        return isImageJpg(file) || isImagePng(file) || isImageGif(file) || isImageJpeg(file) && isNormalSize(file);
    }

    /**
     * Валидирует является ли графический файл файлом с расширение .jpg, если нет тогда ошибка валидации
     * @param graphicFile загружаемый графический файл
     * @return файл
     */
    private static boolean isImageJpg(final MultipartFile graphicFile){
        return Objects.equals(graphicFile.getContentType(), FILE_JPG) && Objects.equals(FilenameUtils.getExtension(graphicFile.getOriginalFilename()), EXTENSION_JPG);
    }

    /**
     * Валидирует является ли графический файл файлом с расширение .jpeg, если нет тогда ошибка валидации
     * @param graphicFile загружаемый графический файл
     * @return файл
     */
    private static boolean isImageJpeg(final MultipartFile graphicFile){
        return Objects.equals(graphicFile.getContentType(), FILE_JPG) && Objects.equals(FilenameUtils.getExtension(graphicFile.getOriginalFilename()), EXTENSION_JPEG);
    }
    /**
     * Валидирует является ли графический файл файлом с расширением .png, если нет тогда ошибка валидации
     * @param graphicFile загружаемый графический файл
     * @return ошибка или true
     */
    private static boolean isImagePng(final MultipartFile graphicFile){
        return Objects.equals(graphicFile.getContentType(), FILE_PNG) && Objects.equals(FilenameUtils.getExtension(graphicFile.getOriginalFilename()), EXTENSION_PNG);
    }

    /**
     * Валидирует является ли графический файл файлом с расширением .gif, если нет тогда ошибка валидации
     * @param graphicFile загружаемый графический файл
     * @return ошибка или true
     */
    private static boolean isImageGif(final MultipartFile graphicFile){
        return Objects.equals(graphicFile.getContentType(), FILE_GIF) && Objects.equals(FilenameUtils.getExtension(graphicFile.getOriginalFilename()), EXTENSION_GIF);
    }

    /**
     * Валидирует является ли графический файл файлом с расширение .jpg .png .gif, если нет тогда ошибка валидации
     * @param graphicFile загружаемый графический файл
     * @return ошибка или true
     */
    private static boolean isNormalSize(final MultipartFile graphicFile){
        if(graphicFile.getSize()> imageSize){
            throw new JuliaValidationException("Размер фотографии не должен превышать 2Mb");
        }
        return true;
    }

}
