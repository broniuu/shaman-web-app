package com.example.shamanApi.exception;

/**
 * Służy do wyłapywania wyjątku rzucanego przy użytkowniku
 */
public class UserAlreadyExistException extends RuntimeException {
    /**
     * Zwraca komunikat o błędzie związanym z zajętym loginem lub hasłem użytkownika
     *
     * @param message   komunikat o błędzie związanym z zajętym loginem lub hasłem użytkownika
     */
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
