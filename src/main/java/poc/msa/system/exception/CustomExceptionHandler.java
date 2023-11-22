package poc.msa.system.exception;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import poc.msa.system.dto.APIErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler {
        
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, Locale locale) {
        APIErrorResponse errorResponse = new APIErrorResponse();
        errorResponse.setTimestamp(Calendar.getInstance().getTime());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(exception.getMessage());
        for (ConstraintViolation<?> violation: exception.getConstraintViolations()) {
            errorResponse.getConstraintErrors().add(messageSource.getMessage(violation.getMessage(), null, locale));
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
