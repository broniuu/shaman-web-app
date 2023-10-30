package com.example.shamanApi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utilities {
    static public boolean checkUser(String login){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName().equals(login);
    }
}
