package julia.service.file_data_load.csv_processors;

import com.opencsv.CSVReader;
import julia.utils.DateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Обработчик csv-файла
 * @author vandronov
 */
public abstract class CSVProcessor{

    private final char separator;

    private final char quotechar;

    /**
     * Конструктор
     */
    public CSVProcessor() {
        separator = ',';
        quotechar = '\'';
    }

    /**
     * Конструктор
     * @param separator символ для отделения элементов
     * @param quotechar символ для кавычек
     */
    public CSVProcessor(char separator, char quotechar) {
        this.separator = separator;
        this.quotechar = quotechar;
    }

    /**
     * Обработать csv-файл
     * @param file csv-файл
     */
    public void process(File file) {
        try (FileInputStream fis = new FileInputStream(file); InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                CSVReader reader = new CSVReader(isr, separator, quotechar)) {
            beforeProcess();
            for (String[] values : reader.readAll()) {
                process(values);
            }
            afterProcess();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String processCSVValue(String value) {
        return value == null || value.trim().equalsIgnoreCase("NULL") ? null : value.trim();
    }

    public Date processCSVDate(String value) {
        String date = processCSVValue(value);
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DD_MM_YYYY_PATTERN);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Вызывается каждый раз при обработке строки из csv-файла
     * @param values значения из обрабатываемой строки
     */
    protected abstract void process(String[] values);

    /**
     * Вызывается после обработки всех строк
     */
    protected void afterProcess() {
    }

    /**
     * Вызывается до начала обработки строк
     */
    protected void beforeProcess() {
    }

}
