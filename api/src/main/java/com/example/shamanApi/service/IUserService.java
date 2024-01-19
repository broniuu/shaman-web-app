package com.example.shamanApi.service;

import com.example.shamanApi.dto.*;

import java.util.List;

/**
 * Interfejs do obsługi klasy User
 */
public interface IUserService {
    UserDto registerNewUserAccount(UserDto userDto);
    UserDto deleteUserAccount(String login);

    EditUserDto updateUserAccount(String login, EditUserDto userDto);

    ShortUserInfoDto updateUser(ShortUserInfoDto userDto);

    UserDto showUserAccount(String login);

    List<RoleDto> showUserRoles(String login);

    List<ShortUserInfoDto> showAllUsers();
    public EditUserDto updateUserPassword(String login, ChangePasswordDto changePasswordDto);
    boolean checkIfLoggedUserHasRole(String roleName);
}
