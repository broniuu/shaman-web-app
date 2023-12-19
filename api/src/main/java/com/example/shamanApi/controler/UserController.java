package com.example.shamanApi.controler;

import com.example.shamanApi.dto.LoginRequest;
import com.example.shamanApi.dto.RoleDto;
import com.example.shamanApi.dto.ShortUserInfoDto;
import com.example.shamanApi.dto.UserDto;
import com.example.shamanApi.exception.UnauthorizedException;
import com.example.shamanApi.repository.UserRepository;
import com.example.shamanApi.service.TokenService;
import com.example.shamanApi.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.UUID;

import static com.example.shamanApi.security.Utilities.checkUser;

/**
 * Klasa obsługuje endpointy związane z użytkownikiem
 */
@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public UserController(UserRepository userRepository, UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Rejestracja użytkownika
     *
     * @param userDto               dane użytkonika do rejestracji
     * @return                      zarejestrowany użytkownik
     * @throws RuntimeException
     */
    @PostMapping(value ="/user/registration")
    public ResponseEntity<UserDto>registerUserAccount(@RequestBody UserDto userDto) throws RuntimeException {
        UserDto registered = userService.registerNewUserAccount(userDto);

        return new ResponseEntity<>(registered, HttpStatus.OK);
    }

    /**
     * Logowanie użytkownika
     *
     * @param userLogin dane do logowania użytkownika
     * @return zalogowany użytkownik
     * @throws AuthenticationException
     */
    @PostMapping(value ="/user/login")
    public Cookie token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.login(), userLogin.password()));
        String token = tokenService.generateToken(authentication);
        UUID loggedUserId = userRepository.findByLogin(userLogin.login()).getUserId();
        Cookie jwtTokenCookie = new Cookie(loggedUserId.toString() ,token);
        jwtTokenCookie.setMaxAge(864000000);
        jwtTokenCookie.setSecure(false);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");

        return jwtTokenCookie;
    }

    /**
     * Usuwanie konta użytkownika
     *
     * @param login     login do usuwanego konta
     * @return          usuwany użytkownik
     */
    @DeleteMapping(value = "{login}/user/delete")
    public ResponseEntity<UserDto> deleteUserAccount(@PathVariable String login) {
        UserDto deleted = null;
        if(checkUser(login) || userService.checkIfLoggedUserHasRole("admin")){
            deleted = userService.deleteUserAccount(login);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Aktualizowanie danych użytkownika
     *
     * @param login     login do aktualizowanego konta
     * @param userDto   dane do aktualizacji
     * @return          zaktualizowany użytkownik
     */
    @PostMapping(value = "{login}/user/update")
    public ResponseEntity<UserDto> updateUserAccount(@PathVariable String login, @RequestBody UserDto userDto) {
        if(checkUser(login) || userService.checkIfLoggedUserHasRole("admin")){
            UserDto updated = userService.updateUserAccount(login, userDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping(value = "users/update")
    public ResponseEntity<ShortUserInfoDto> updateUser(@RequestBody ShortUserInfoDto userDto) {
        if(userService.checkIfLoggedUserHasRole("admin")){
            ShortUserInfoDto updated = userService.updateUser(userDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Wyświetlenie danych użytkownika
     *
     * @param login     login do wyświetlanego konta
     * @return          wyświetlany użytkownik
     */
    @GetMapping(value = "{login}/user")
    public ResponseEntity<UserDto> showUserAccount(@PathVariable String login) {
        UserDto shown = null;
        if(checkUser(login) || userService.checkIfLoggedUserHasRole("admin")){
             shown = userService.showUserAccount(login);
            return new ResponseEntity<>(shown, HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<ShortUserInfoDto>> showAllUsers() {
        if (!userService.checkIfLoggedUserHasRole("admin")) {
            throw new UnauthorizedException("Tylko administratorzy mają dostęp do innych użytkowników");
        }
        List<ShortUserInfoDto> users = userService.showAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<RoleDto>> showAllRoles() {
        if (!userService.checkIfLoggedUserHasRole("admin")) {
            throw new UnauthorizedException("Tylko administratorzy mają dostęp do ról");
        }
        List<RoleDto> roles = userService.showAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
