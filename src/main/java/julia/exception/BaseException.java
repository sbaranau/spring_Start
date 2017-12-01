package julia.exception;


/**
 * Базовый класс для ошибок возникших в сервисе
 * @author Vadim Marchuk
 */
public class BaseException extends RuntimeException {

    /**
     * Серийный номер
     */
    private static final long serialVersionUID = 1L;

    /**
     * Конструктор по умолчанию
     */
    public BaseException() {
        super();
    }


    /**
     * Конструктор, в который передается сообщение
     * @param message сообщение
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * Конструктор, в который передается родительская ошибка
     * @param ex ошибка
     */
    public BaseException(Throwable ex) {
        super(ex);
    }

    /**
     * Конструктор, в который передается сообщение и родительская ошибка
     * @param message сообщение
     * @param ex ошибка
     */
    public BaseException(String message, Throwable ex) {
        super(message, ex);
    }

}
