package julia.dto;

import julia.enums.ErrorTypes;

/**
 *
 *
 * @author Pudul Yuriy
 */
public class ExceptionDTO {

    private String message;
    private ErrorTypes type;

    public ExceptionDTO() {
    }

    public ExceptionDTO(final String message) {
        this.message = message;
    }

    public ExceptionDTO(final String message, final ErrorTypes type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorTypes getType() {
        return type;
    }

    public void setType(ErrorTypes type) {
        this.type = type;
    }
}
