package com.example.shamanApi.repository;

import com.example.shamanApi.model.Discount;
import com.example.shamanApi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Służy do obłśugi zniżek w bazie danych
 */
public interface DiscountRepository extends CrudRepository<Discount, UUID> {
    List<Discount> findAllByUsersWhoUsedThisDiscount(String login);
    boolean existsByDiscountIdAndUsersWhoUsedThisDiscount(UUID discountId, User userWhoUsedThisDiscount);
    Optional<Discount> findByDiscountCode(String discountCode);
    /**
     * znajduje zniżkę, na podane danie
     *
     * @param dishId    ID dania
     * @return          zniżka
     */
    Discount findByDishId(UUID dishId);
}
