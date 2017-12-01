package julia.exception;

/**
 * Ексепшен API сервера
 *
 * @author Pudul Yuriy
 */
public class JuliaApiException extends BaseException {

    /**
     * Констурктор по умолчанию
     */
    public JuliaApiException() {
        super();
    }

    /**
     * Конструкртор, в который передается сообщение
     * @param message сообщение
     */
    public JuliaApiException(final String message) {
        super(message);
    }

    /**
     * Конуструктор, в который передается родительская ошибка
     * @param ex ошибка
     */
    public JuliaApiException(final Throwable ex) {
        super(ex);
    }

    /**
     * Конструктор, в который передается сообщение и родительская ошибка
     * @param message сообщение
     * @param ex ошибка
     */
    public JuliaApiException(final String message, final Throwable ex) {
        super(message, ex);
    }

}
