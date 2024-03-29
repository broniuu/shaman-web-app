package com.example.shamanApi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Służy do wyłapywania wyjątków rzucanych przez kontrollery
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Zwraca komunikat o błędzie związanym z tworzeniem nowego konta
     *
     * @param exception błąd wyrzucony przez endpoint
     * @param request   wywołane zapytanie
     * @return          enkapsulowana wiadomość o błędzie
     */
    @ExceptionHandler(value = UserAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException(UserAlreadyExistException exception, WebRequest request)
    {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
        logError(exception);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private void logError(Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(exception.getMessage()).append('\n');
        exception.getStackTrace();
        for (StackTraceElement element : exception.getStackTrace()){
            stringBuilder.append(element.toString()).append('\n');
        }
        logger.error(stringBuilder.toString());
    }

    /**
     * Zwraca komunikat o błędzie związanym z brakiem szukanego zasobu
     *
     * @param ex        błąd wyrzucony przez endpoint
     * @param request   wywołane zapytanie
     * @return          enkapsulowana wiadomość o błędzie
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> unauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Obsługuje wszystkie wyjątki, które nie są obługiwane przez inne metody w tej klasie
     *
     * @param ex        błąd wyrzucony przez endpoint
     * @param request   wywołane zapytanie
     * @return          enkapsulowana wiadomość o błędzie
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
