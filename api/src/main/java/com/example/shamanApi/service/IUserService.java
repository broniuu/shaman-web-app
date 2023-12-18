package com.example.shamanApi.service;

import com.example.shamanApi.dto.UserDto;

import java.util.List;

/**
 * Interfejs do obsługi klasy User
 */
public interface IUserService {
    UserDto registerNewUserAccount(UserDto userDto);
    UserDto deleteUserAccount(String login);
    UserDto updateUserAccount(String login, UserDto userDto);
    UserDto showUserAccount(String login);

    List<UserDto> showAllUsers();

    boolean checkIfLoggedUserHasRole(String roleName);
}
