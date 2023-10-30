package com.example.shamanApi.dto;
/**
 * rekord używany do przekazywania informaij logowania.
 *
 * @param login     login użytkownika
 * @param password      haslo użytkownika
 * */
public record LoginRequest(String login, String password) {

}
