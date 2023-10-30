package com.example.shamanApi.exception;

/**
 * Służy do wyłapywania wyjątku rzucanego przy restauracji
 */
public class RestarurantNotFoundException extends RuntimeException {
    /**
     * Zwraca komunikat o błędzie związanym z brakiem restauracji
     *
     * @param message   komunikat o błędzie związanym z brakiem restauracji
     */
    public RestarurantNotFoundException(String message) {
        super(message);
    }
}
