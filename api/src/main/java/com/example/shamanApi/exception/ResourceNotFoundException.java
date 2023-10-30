package com.example.shamanApi.exception;

/**
 * Służy do wyłapywania wyjątku rzucanego przy koszyku
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Zwraca komunikat o błędzie związanym z koszykiem
     *
     * @param message   komunikat o błędzie związanym z koszykiem
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
