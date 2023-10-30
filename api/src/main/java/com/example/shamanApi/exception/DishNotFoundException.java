package com.example.shamanApi.exception;

/**
 * Służy do wyłapywania wyjątku rzucanego przy daniach
 */
public class DishNotFoundException extends RuntimeException {
    /**
     * Zwraca komunikat o błędzie związanym z brakiem dania
     *
     * @param message   komunikat o błędzie związanym z brakiem dania
     */
    public DishNotFoundException(String message) {
        super(message);
    }
}
