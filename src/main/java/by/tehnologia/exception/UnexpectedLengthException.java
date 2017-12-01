package by.tehnologia.exception;

/**
 * @author Aleksandr Golovnyov
 */
public class UnexpectedLengthException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnexpectedLengthException() {
        super();
    }

    public UnexpectedLengthException(String message) {
        super(message);
    }

    public UnexpectedLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedLengthException(Throwable cause) {
        super(cause);
    }
}
