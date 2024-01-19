package com.example.shamanApi.dto;

import com.sun.istack.NotNull;
import jakarta.validation.constraints.NotEmpty;

public class ChangePasswordDto {
    @NotNull
    @NotEmpty
    private String Login;
    @NotNull
    @NotEmpty
    private String OldPassword;
    @NotNull
    @NotEmpty
    private String Password;
    @NotNull
    @NotEmpty
    private String RepeatPassword;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRepeatPassword() {
        return RepeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        RepeatPassword = repeatPassword;
    }
}
