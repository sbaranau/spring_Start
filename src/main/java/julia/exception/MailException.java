package julia.exception;


/**
 * @author Vadim Marchuk
 */
public class MailException extends BaseException {

    /**
     * Серийный номер
     */
    private static final long serialVersionUID = 1L;

    /**
     * Констурктор по умолчанию
     */
    public MailException() {
        super();
    }

    /**
     * Конструкртор, в который передается сообщение
     * @param message сообщение
     */
    public MailException(String message) {
        super(message);
    }

    /**
     * Конуструктор, в который передается родительская ошибка
     * @param ex ошибка
     */
    public MailException(Throwable ex) {
        super(ex);
    }

    /**
     * Конструктор, в который передается сообщение и родительская ошибка
     * @param message сообщение
     * @param ex ошибка
     */
    public MailException(String message, Throwable ex) {
        super(message, ex);
    }

}
