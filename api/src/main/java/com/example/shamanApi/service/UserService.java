package com.example.shamanApi.service;

import com.example.shamanApi.dto.*;
import com.example.shamanApi.exception.PasswordNotCorrectException;
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
import java.util.*;

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
        userToRegister.setUserId(UUID.randomUUID());
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
     * @param editUserDto dane do aktualizacji
     * @return zaktualizowany użytkownik
     */
    @Override
    public EditUserDto updateUserAccount(String login, EditUserDto editUserDto) {
        User userToUpdate = userRepository.findByLogin(login);
        if (userToUpdate == null) {
            throw new UserNotFoundException("Nie ma użytkownika z tym loginem: "
                    + login);
        }
        changeEditUserDtoToUser(editUserDto, userToUpdate);
        userRepository.save(userToUpdate);
        return mapper.map(userToUpdate, EditUserDto.class);
    }
    @Override
    public EditUserDto updateUserPassword(String login, ChangePasswordDto changePasswordDto) {

        User userToUpdate = userRepository.findByLogin(login);
        if (userToUpdate == null) {
            throw new UserNotFoundException("Nie ma użytkownika z tym loginem: "
                    + login);
        }
        if(changePasswordDto.getOldPassword().equals(userToUpdate.getPassword())){
            throw new PasswordNotCorrectException("Hasło nie poprawne dla użytkownika: "
                    + login);
        }
        userToUpdate.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(userToUpdate);
        return mapper.map(userToUpdate, EditUserDto.class);
    }
    @Override
    public ShortUserInfoDto updateUser(ShortUserInfoDto userDto) {
        Optional<User> userToUpdateOptional = userRepository.findById(userDto.getUserId());
        if (userToUpdateOptional.isEmpty()) {
            throw new UserNotFoundException("Nie można edytować nieistniejącego użytkownika");
        }
        User userToUpdate = userToUpdateOptional.get();
        userToUpdate.setLogin(userDto.getLogin());
        userToUpdate.setName(userDto.getName());
        userToUpdate.setSurname(userDto.getName());
        userToUpdate.setEmail(userDto.getEmail());
        List<Role> rolesToUpdate = new ArrayList<>();
        for (RoleDto roleDto : userDto.getRoles()) {
            Optional<Role> roleOptional = roleRepository.findById(roleDto.getRoleId());
            if (roleOptional.isEmpty()){
                continue;
            }
            rolesToUpdate.add(roleOptional.get());
        }
        userToUpdate.setRoles(rolesToUpdate);
        userRepository.save(userToUpdate);
        return userDto;
    }

    /**
     * Konwertuje obiekt EditUserDto do obietku User
     *
     * @param editUserDto           obiekt do konwersji
     * @param userToUpdate      skonwertowany obiekt User
     */
    private void changeEditUserDtoToUser(EditUserDto editUserDto, User userToUpdate) {
        userToUpdate.setLogin(editUserDto.getLogin());
        userToUpdate.setName(editUserDto.getName());
        userToUpdate.setSurname(editUserDto.getSurname());
        userToUpdate.setAddress(editUserDto.getAddress());
        userToUpdate.setDebitCardNumber(editUserDto.getDebitCardNumber());
        userToUpdate.setExpireDate(editUserDto.getExpireDate());
        userToUpdate.setCvv(editUserDto.getCvv());
        userToUpdate.setEmail(editUserDto.getEmail());
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
    public List<RoleDto> showUserRoles(String login) {
        if (userRepository.findByLogin(login) == null) {
            throw new UserNotFoundException("Nie ma użytkownika z tym loginem: "
                    + login);
        }
        User user = userRepository.findByLogin(login);
        return user.getRoles().stream().map(r -> mapper.map(r, RoleDto.class)).toList();
    }

    @Override
    public List<ShortUserInfoDto> showAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<ShortUserInfoDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(mapper.map(user, ShortUserInfoDto.class));
        }
        return userDtos;
    }

    @Override
    public boolean checkIfLoggedUserHasRole(String roleName) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        return user.getRoles().stream().anyMatch(r -> Objects.equals(r.getName(), roleName));
    }

    public List<RoleDto> showAllRoles() {
        Iterable<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : roles) {
            roleDtos.add(mapper.map(role, RoleDto.class));
        }
        return roleDtos;
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
