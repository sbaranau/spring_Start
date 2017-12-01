package julia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Утилитный класс для работы с датой.
 *
 * @author Artiom_Buevich
 *
 */
public final class DateUtils {

    public static final String DD_MM_YYYY_PATTERN = "dd.MM.yyyy";
    public static final String DD_MM_YYYY_HH_MM_PATTERN = "dd.MM.yyyy HH:mm";

    public static int year(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    private DateUtils() {
    }

    /**
     * Увеличевает год в заданной Дате на заданное число
     * @param date Дата в которой необходимо увеличить год
     * @param year год, который прибавляем к дате
     * @return новая дата с прибавленным годом
     */
    public static Date increaseYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int summYear = calendar.get(Calendar.YEAR);
        summYear = summYear + year;
        calendar.set(Calendar.YEAR,  summYear);
        return calendar.getTime();
    }

    /**
     * Прибавить один день к дате
     * @param date дата
     * @return дата плюс один день
     */
    public static Date increaseDay(final Date date, int countOfDays) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, countOfDays);
        return calendar.getTime();
    }

    public static Date getMonthActualMaximum() {
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static Date getMonthActualMinimum() {
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Переводит строку в дату
     * @param date строка даты
     * @param pattern формат даты
     * @return дата в нужном формате
     */
    public static Date stringToDate(final String date, final String pattern) {
        final SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (final ParseException ex) {
            return Calendar.getInstance().getTime();
        }
    }

    /**
     * Получить дату со временем
     *
     * @param date дата
     * @return дата со временем 8:00
     */
    public static Date addDefaultTimeToDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        return calendar.getTime();
    }
}
