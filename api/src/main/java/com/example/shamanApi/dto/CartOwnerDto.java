package com.example.shamanApi.dto;

import java.util.UUID;

public class CartOwnerDto {
    private UUID userId;
    private String login;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
