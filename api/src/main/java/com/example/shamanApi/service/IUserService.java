package com.example.shamanApi.service;

import com.example.shamanApi.dto.UserDto;
import com.example.shamanApi.model.User;

/**
 * Interfejs do obsługi klasy User
 */
public interface IUserService {
    User registerNewUserAccount(UserDto userDto);
    User deleteUserAccount(String login);
    User updateUserAccount(String login, UserDto userDto);
    User showUserAccount(String login);
    boolean checkRoleOfLoggedUser(String roleName);
}
