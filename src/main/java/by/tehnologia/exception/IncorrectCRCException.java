package by.tehnologia.exception;

/**
 * @author Aleksandr Golovnyov
 */
public class IncorrectCRCException extends Exception {

    private static final long serialVersionUID = 1L;

    public IncorrectCRCException() {
        super();
    }

    public IncorrectCRCException(String message) {
        super(message);
    }

    public IncorrectCRCException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCRCException(Throwable cause) {
        super(cause);
    }
}
