package julia.web.mvc;

import julia.dto.ExceptionDTO;
import julia.enums.ErrorTypes;
import julia.exception.JuliaApiException;
import julia.exception.JuliaValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice
public class GlobalController {

    private final static Logger logger = LoggerFactory.getLogger(GlobalController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object handleException(final Exception e, final HttpServletRequest request) {
        if (isAjax(request)) {
            logger.error("Exception in REST controller.", e);
            return handleJuliaApiException(null, request);
        }
        logger.error("Exception", e);
        return new ResponseForm(null, ControllerConstants.RESULT_SERVER_ERROR);
    }

    /**
     * Хандлер ошибок JuliaApiException
     * Приводит ошибку в удобный для фронта
     * логирает и выставляет статус ошибки
     * @param e ошибка
     * @param request запрос
     *
     * @return see {@link ExceptionDTO}
     */
    @ExceptionHandler(JuliaApiException.class)
    public ResponseEntity<ExceptionDTO> handleJuliaApiException(final Exception e, final HttpServletRequest request) {
        logger.error("Exception in REST controller.", e);
        return new ResponseEntity<>(new ExceptionDTO(
                Optional.ofNullable(e)
                        .map(ex -> {
                            logger.error(ErrorTypes.API_ERROR.getName(), ex);
                            return ex.getMessage();
                        })
                        .orElse(null),
                ErrorTypes.API_ERROR
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Хандлер ошибок JuliaValidationException
     * Приводит ошибку в удобный для фронта
     * логирает и выставляет статус ошибки
     * @param e ошибка
     * @param request запрос
     *
     * @return see {@link ExceptionDTO}
     */
    @ExceptionHandler(JuliaValidationException.class)
    public ResponseEntity<ExceptionDTO> handleValidationApiException(final Exception e, final HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionDTO(
                Optional.ofNullable(e)
                        .map(ex -> {
                            logger.error(ErrorTypes.VALIDATION_ERROR.getName(), ex);
                            return ex.getMessage();
                        })
                        .orElse(null),
                ErrorTypes.VALIDATION_ERROR
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Проверить является ли запрос AJAX-запросом
     * @param request запрос
     * @return <code>true</code> если AJAX, иначе <code>false</code>
     */
    private boolean isAjax(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return header != null && header.equals("XMLHttpRequest");
    }
}
