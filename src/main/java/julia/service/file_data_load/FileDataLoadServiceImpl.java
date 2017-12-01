package julia.service.file_data_load;

import julia.service.file_data_load.csv_processors.CSVProcessor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Сервис для загрузки данных из csv-файлов
 * @author vandronov
 */
@Service
public class FileDataLoadServiceImpl implements FileDataLoadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDataLoadServiceImpl.class);

    @Autowired
    private FileDataLoader fileDataLoader;

    @Override
    public void loadAll() {
    }

    /**
     * Вспомогательный класс для работоспособности REQUIRES_NEW
     */
    @Service
    public static class FileDataLoader {

        @Resource
        @Qualifier("settings")
        private Map<String, String> settings;


        private void processCSVFile(String filePath, CSVProcessor processor) {
            try {
                File parsedDirectory = new File(filePath + File.separator + "parsed");
                FileUtils.forceMkdir(parsedDirectory);
                Iterator<File> fileIterator = FileUtils.iterateFiles(new File(filePath), new String[] {"csv"}, false);
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
                while (fileIterator.hasNext()) {
                    File file = fileIterator.next();
                    processor.process(file);
                    String newFileExtension = "." + sdf.format(new Date());
                    FileUtils.moveFile(file, new File(parsedDirectory.getAbsolutePath() + File.separator + file.getName() + newFileExtension));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
