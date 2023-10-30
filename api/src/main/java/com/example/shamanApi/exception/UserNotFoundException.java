package com.example.shamanApi.exception;

/**
 * Służy do wyłapywania wyjątku rzucanego przy użytkowniku
 */
public class UserNotFoundException extends RuntimeException{
    /**
     * Zwraca komunikat o błędzie związanym z brakiem użytkownika o podanym login lub hasło
     *
     * @param message   komunikat o błędzie związanym z brakiem użytkownika o podanym login lub hasło
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
