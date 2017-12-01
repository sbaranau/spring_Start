package julia.exception;

/**
 * @author Aleksandr Golovnyov
 */
public class SensorResponseException extends Exception {

    private static final long serialVersionUID = 1L;

    public SensorResponseException() { }

    public SensorResponseException(String message) {
        super(message);
    }

    public SensorResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SensorResponseException(Throwable cause) {
        super(cause);
    }
}
