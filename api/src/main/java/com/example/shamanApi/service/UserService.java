package com.example.shamanApi.service;

import com.example.shamanApi.dto.UserDto;
import com.example.shamanApi.exception.UserAlreadyExistException;
import com.example.shamanApi.exception.UserNotFoundException;
import com.example.shamanApi.model.Role;
import com.example.shamanApi.model.User;
import com.example.shamanApi.repository.RoleRepository;
import com.example.shamanApi.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Obsługuje operacje związane z zarządzaniem użytkownikami
 */
@Service
@Transactional
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    /**
     * Rejestracja nowego użytkownika
     *
     * @param userDto                       dane użytkownika do rejestracji
     * @return                              zarejestrowany użytkownik
     * @throws UserAlreadyExistException    wyjątek w przypadku istnienia użytkownika o podanym loginie lub adresie email
     */
    @Override
    public UserDto registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("Istnieje już konto z tym adresem email: "
                    + userDto.getEmail());
        }
        if (loginExists(userDto.getLogin())) {
            throw new UserAlreadyExistException("Istnieje już konto z tym loginem: "
                    + userDto.getLogin());
        }
        User userToRegister = mapper.map(userDto,User.class);
        userToRegister.setLogin(userDto.getLogin());
        encodePassword(userToRegister, userDto);
        Role userRole = roleRepository.findByName("user");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        userToRegister.setRoles(roles);
        User addedUser = userRepository.save(userToRegister);
        return mapper.map(addedUser, UserDto.class);
    }

    /**
     * Usuwanie konta użytkownika
     *
     * @param login login do usuwanego konta
     * @return usuwany użytkownik
     */
    @Override
    public UserDto deleteUserAccount(String login) {
        User userToDelete = userRepository.findByLogin(login);
        if (userToDelete == null) {
            throw new UserNotFoundException("Nie ma użytkownika z tym loginem: "
                    + login);
        }
        userRepository.delete(userToDelete);
        return mapper.map(userToDelete, UserDto.class);
    }

    /**
     * Aktualizowanie danych użytkownika
     *
     * @param login   login do aktualizowanego konta
     * @param userDto dane do aktualizacji
     * @return zaktualizowany użytkownik
     */
    @Override
    public UserDto updateUserAccount(String login, UserDto userDto) {
        User userToUpdate = userRepository.findByLogin(login);
        if (userToUpdate == null) {
            throw new UserNotFoundException("Nie ma użytkownika z tym loginem: "
                    + login);
        }
        changeUserDtoToUser(userDto, userToUpdate);
        userRepository.save(userToUpdate);
        return mapper.map(userToUpdate, UserDto.class);
    }

    /**
     * Konwertuje obiekt UserDto do obietku User
     *
     * @param userDto           obiekt do konwersji
     * @param userToUpdate      skonwertowany obiekt User
     */
    private void changeUserDtoToUser(UserDto userDto, User userToUpdate) {
        userToUpdate.setLogin(userDto.getLogin());
        encodePassword(userToUpdate, userDto);
        userToUpdate.setName(userDto.getName());
        userToUpdate.setSurname(userDto.getSurname());
        userToUpdate.setAddress(userDto.getAddress());
        userToUpdate.setDebitCardNumber(userDto.getDebitCardNumber());
        userToUpdate.setExpireDate(userDto.getExpireDate());
        userToUpdate.setCvv(userDto.getCvv());
        userToUpdate.setEmail(userDto.getEmail());
    }

    /**
     * Wyświetlenie danych użytkownika
     *
     * @param login login do wyświetlanego konta
     * @return wyświetlany użytkownik
     */
    @Override
    public UserDto showUserAccount(String login) {
        if (userRepository.findByLogin(login) == null) {
            throw new UserNotFoundException("Nie ma użytkownika z tym loginem: "
                    + login);
        }
        User user = userRepository.findByLogin(login);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public boolean checkRoleOfLoggedUser(String roleName) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        return user.getRoles().stream().anyMatch(r -> Objects.equals(r.getName(), roleName));
    }

    /**
     * Sprawdzenie czy podany email jest używany
     *
     * @param email     email do sprawdzenia
     * @return          true jeśli podany mail jest używany lub false jeśli nie jest
     */
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Sprawdzenie czy podany login jest używany
     *
     * @param login     login do sprawdzenia
     * @return          true jeśli podany login jest używany lub false jeśli nie jest
     */
    private boolean loginExists(String login) {
        return userRepository.findByLogin(login) != null;
    }

    /**
     * Kodowanie hasła użytkownika
     *
     * @param user      użytkownik
     * @param userDto   hasło do zakodowania
     */
    private void encodePassword( User user, UserDto userDto){
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
}
