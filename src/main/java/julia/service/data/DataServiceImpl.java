package julia.service.data;

import julia.dao.DataDao;
import julia.entity.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Ivan Shyrma
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataDao dataDao;

    /**
     * @param data file
     * @return Long data id
     */
    @Override
    public Long save(Data data) {
        dataDao.create(data);
        return data.getSensorId();
    }

    @Override
    public Long update(final Data data) {
        dataDao.update(data);
        return data.getId();
    }

    @Override
    public Data createEntity(final String mime, final String name, final byte[] convertedFileData, final Long sensorId) {
        final Data data = new Data();
        data.setData(convertedFileData);
        data.setName(name);
        data.setMimeType(mime);
        data.setSensorId(sensorId);
        return data;
    }

    @Override
    public Data getDataBySensorId(Long sensorId) {
        return dataDao.readBySensorId(sensorId);
    }

    @Override
    public Data getDataById(Long id) {
        return dataDao.read(id);
    }

    @Override
    public Data convertMultipartFileForSensor(MultipartFile multipartFile, Long sensorId) {
        Data data = new Data();
        try {
            data.setSensorId(sensorId);
            data.setName(multipartFile.getOriginalFilename());
            data.setData(multipartFile.getBytes());
            data.setMimeType(multipartFile.getContentType());
            if (getDataBySensorId(sensorId) == null) {
                dataDao.create(data);
            } else {
                dataDao.updateBySensorId(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long convertMultipartFile(final MultipartFile multipartFile) {
        return convertMultipartFile(multipartFile, null);
    }

    @Override
    public Long convertMultipartFile(MultipartFile multipartFile, Long id) {
        Data data = new Data();
        try {
            data.setSensorId(null);
            data.setName(multipartFile.getOriginalFilename());
            data.setData(multipartFile.getBytes());
            data.setMimeType(multipartFile.getContentType());
            if (id == null) {
                dataDao.create(data);
            } else {
                data.setId(id);
                dataDao.update(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.getId();
    }

    @Override
    public void deleteData(Long id) {
        dataDao.delete(id);
    }

    @Override
    public String getDataNameById(Long id) {
        return dataDao.readDataNameById(id);
    }

}
