package julia.exception;

/**
 * Ексепшен API валидации
 *
 * @author Pudul Yuriy
 */
public class JuliaValidationException extends BaseException {

    /**
     * Констурктор по умолчанию
     */
    public JuliaValidationException() {
        super();
    }

    /**
     * Конструкртор, в который передается сообщение
     * @param message сообщение
     */
    public JuliaValidationException(final String message) {
        super(message);
    }

    /**
     * Конуструктор, в который передается родительская ошибка
     * @param ex ошибка
     */
    public JuliaValidationException(final Throwable ex) {
        super(ex);
    }

    /**
     * Конструктор, в который передается сообщение и родительская ошибка
     * @param message сообщение
     * @param ex ошибка
     */
    public JuliaValidationException(final String message, final Throwable ex) {
        super(message, ex);
    }

}
