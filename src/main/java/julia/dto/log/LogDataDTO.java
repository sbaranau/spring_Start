package julia.dto.log;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Alexander Demidovich
 *
 * Класс описывает LOG файл
 */
public class LogDataDTO implements Serializable{
    /**
     * Дата создания LogDataDTO - файла
     */
    private Date date;
    /**
     * Имя LogDataDTO - файла
     */
    private String name;

    public LogDataDTO(Date date, String name){
        this.date = date;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Comparator<LogDataDTO>  compareByDate = (o1, o2) -> o2.getDate().compareTo(o1.getDate());

}
