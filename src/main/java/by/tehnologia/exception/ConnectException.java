package by.tehnologia.exception;

/**
 * @author Aleksandr Golovnyov
 */
public class ConnectException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConnectException() {
        super();
    }

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectException(Throwable cause) {
        super(cause);
    }
}
