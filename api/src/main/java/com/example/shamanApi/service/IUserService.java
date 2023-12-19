package com.example.shamanApi.service;

import com.example.shamanApi.dto.RoleDto;
import com.example.shamanApi.dto.ShortUserInfoDto;
import com.example.shamanApi.dto.UserDto;

import java.util.List;

/**
 * Interfejs do obsługi klasy User
 */
public interface IUserService {
    UserDto registerNewUserAccount(UserDto userDto);
    UserDto deleteUserAccount(String login);
    UserDto updateUserAccount(String login, UserDto userDto);

    ShortUserInfoDto updateUser(ShortUserInfoDto userDto);

    UserDto showUserAccount(String login);

    List<RoleDto> showUserRoles(String login);

    List<ShortUserInfoDto> showAllUsers();

    boolean checkIfLoggedUserHasRole(String roleName);
}
