package com.example.shamanApi.service;

import com.example.shamanApi.model.User;
import com.example.shamanApi.repository.UserRepository;
import com.example.shamanApi.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/**
 * klasa umożliwiająca znalezienie użytkownika o podanym loginie.
 *
 * {@link UserRepository UserRepository}
 * implementuje UserDetailsService.
 * */
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    /**
     * funkcja sprawdza czy login podany przu logowaniu istnieje, jezeli tak zwraca CustomUserDetails.
     *
     * @param login     login po którym bedzie szukany urzytkownik
     * @return Zwraca klase CustomUserDetails z użytkownikiem znalezionym po loginie
     * */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
