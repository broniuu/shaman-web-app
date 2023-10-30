package com.example.shamanApi.repository;

import com.example.shamanApi.model.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * Służy do obłśugi dań z koszyka w bazie danych
 */
@Repository
public interface CartItemRepository extends PagingAndSortingRepository<CartItem, UUID> {
    /**
     * Znajduje wszystkie dania dla danego użytkownika
     *
     * @param login login właściciela koszyka
     * @return      dania z koszyka
     */
    @Query("SELECT ci FROM CartItem ci INNER join User u ON u.userId = ci.cartOwnerId  WHERE u.login = ?1")
    Iterable<CartItem> findAllByCartOwnerLogin(String login);

    /**
     * Znajduje wszystkie dania na podanej stronie dla danego użytkownika
     *
     * @param cartOwnerId   ID właściciela koszyka
     * @param pageable      parametry do pagingu
     * @return              dania z koszyka
     */
    @Query("SELECT ci FROM CartItem ci WHERE ci.cartOwnerId = ?1")
    Page<CartItem> findAllByCartOwnerId(UUID cartOwnerId, Pageable pageable);

    /**
     * Liczy wszystkie dania danego użytkownika
     *
     * @param cartOwnerId   ID użytkownika
     * @return              liczba dań w koszyku
     */
    int countByCartOwnerId(UUID cartOwnerId);

    /**
     * Zwraca konkretne danie z koszyka danego użytkownika
     *
     * @param ownerLogin    login właściciela koszyka
     * @param cartItemId    ID dania z koszyka
     * @return              danie z koszyka
     */
    @Query("select ci from CartItem ci inner join User u on u.userId = ci.cartOwnerId where u.login = ?1 and ci.cartItemId = ?2")
    Optional<CartItem> findByOwnerLoginAndCartItemId(String ownerLogin, UUID cartItemId);

    /**
     * Zwraca konkretne danie z koszyka danego użytkownika
     *
     * @param ownerLogin    login właściciela koszyka
     * @param dishId        ID dania
     * @return              danie z koszyka
     */
    @Query("select ci from CartItem ci inner join User u on u.userId = ci.cartOwnerId where u.login = ?1 and ci.dishId = ?2")
    Optional<CartItem> findByOwnerLoginAndDishId(String ownerLogin, UUID dishId);

    /**
     * Usówa wszystkie sztuki dania z koszyka
     *
     * @param id ID dania z koszyka
     */
    @Modifying
    @Transactional
    @Query("delete from CartItem ci WHERE ci.cartItemId = ?1")
    void deleteByCartItemId(UUID id);
}
