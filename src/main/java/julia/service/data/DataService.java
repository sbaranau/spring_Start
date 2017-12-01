package julia.service.data;

import julia.entity.Data;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author Ivan Shyrma
 */
public interface DataService {

    /**
     * @param data data
     * @return Data
     */
    Long save(Data data);

    Long update(final Data data);

    /**
     * создаёт сущьность
     *
     * @return новая заполненная сущьность
     */
    Data createEntity(final String mime, final String name, final byte[] convertedFileData, final Long sensorId);

    /**
     * @param id id data
     * @return Long id
     */
    Data getDataById(Long id);

    /**
     * @param multipartFile документ для сохранения
     * @return multipartFile
     */
    Long convertMultipartFile(MultipartFile multipartFile, Long id);

    /**
     * Создаёт или обновляет существующий вложенный файл
     * @param multipartFile файл
     * @return id записи файла
     */
    Long convertMultipartFile(final MultipartFile multipartFile);

    /**
     * @param sensorId
     * @return
     */
    Data getDataBySensorId(Long sensorId);

    /**
     * @param multipartFile
     * @param sensorId
     * @return
     */
    Data convertMultipartFileForSensor(MultipartFile multipartFile, Long sensorId);

    void deleteData(Long id);

    /**
     * Получить имя данных по идентификатору
     * @param id идентификатор данных
     * @return имя данных
     */
    String getDataNameById(Long id);

}
