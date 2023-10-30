package com.example.shamanApi.exception;

import org.springframework.http.HttpStatus;
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
    /**
     * Zwraca komunikat o błędzie związanym z tworzeniem nowego konta
     *
     * @param exception błąd wyrzucony przez endpoint
     * @param request   wywołane zapytanie
     * @return          enkapsulowana wiadomość o błędzie
     */
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ErrorMessage handleUserAlreadyExistsException(UserAlreadyExistException exception, WebRequest request)
    {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
        return message;
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
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
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
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }
}
