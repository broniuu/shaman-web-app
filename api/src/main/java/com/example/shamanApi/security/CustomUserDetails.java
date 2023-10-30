package com.example.shamanApi.security;

import com.example.shamanApi.model.User;
import com.example.shamanApi.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
/**
 * klasa umożliwiająca logowanie, pobierająca informacje z klasy User.
 * Implementuje UsrDetails.
 * */
public class CustomUserDetails implements UserDetails {
    UserService service;
    PasswordEncoder passwordEncoder;
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getPassword() {
        return ( user.getPassword());
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return user.getName() + " " + user.getSurname();
    }

}
