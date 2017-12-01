package julia.exception;


public class DeleteException extends Exception {

    /**
     * Серийный номер
     */
    private static final long serialVersionUID = 1L;

    /**
     * Конструктор по умолчанию
     */
    public DeleteException() {
        super();
    }


    /**
     * Конструктор, в который передается сообщение
     * @param message сообщение
     */
    public DeleteException(String message) {
        super(message);
    }

    /**
     * Конструктор, в который передается родительская ошибка
     * @param ex ошибка
     */
    public DeleteException(Throwable ex) {
        super(ex);
    }

    /**
     * Конструктор, в который передается сообщение и родительская ошибка
     * @param message сообщение
     * @param ex ошибка
     */
    public DeleteException(String message, Throwable ex) {
        super(message, ex);
    }

}
