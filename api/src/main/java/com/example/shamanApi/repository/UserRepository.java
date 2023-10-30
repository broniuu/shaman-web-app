package com.example.shamanApi.repository;

import com.example.shamanApi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Służy do obsługi użytkownika w bazie danych
 */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    /**
     * Znajduje użytkownika po loginie
     *
     * @param login     login użytkownika
     * @return          użytkownik
     */
    @Query("SELECT u FROM User u WHERE u.login = ?1")
    User findByLogin(String login);

    /**
     * Znajduje użytkownika po emailu
     *
     * @param email     email użytkownika
     * @return          użytkownik
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    /**
     * Znajduje ID użytkownika po loginie
     *
     * @param login     login użytkownika
     * @return          ID użytkownika
     */
    @Query("SELECT u.userId FROM User u WHERE u.login = ?1")
    UUID findUserIdByLogin(String login);
}
