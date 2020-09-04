package pl.codzisnaobiad.imhungry.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.codzisnaobiad.imhungry.api.exception.SpoonacularCommunicationException;

@ControllerAdvice
class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private static final String UNCAUGHT_RUNTIME_EXCEPTION_LOG = "Uncaught runtime exception";

    @ExceptionHandler(SpoonacularCommunicationException.class)
    ResponseEntity<String> onSpoonacularCommunicationException(SpoonacularCommunicationException exception) {
        logger.warn(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> onRuntimeException(RuntimeException exception) {
        logger.error(UNCAUGHT_RUNTIME_EXCEPTION_LOG, exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(exception.getMessage());
    }
}