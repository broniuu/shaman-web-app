package com.example.shamanApi.repository;

import com.example.shamanApi.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
/**
 * Służy do obłśugi dań w bazie danych
 */
@Repository
public interface DishRepository extends PagingAndSortingRepository<Dish, UUID> {
    /**
     *  znajduje danie po id restauracji i nazwie dania
     */
    @Query("SELECT d FROM Dish d WHERE d.restaurantId = ?1 and d.name=?2")
    Optional<Dish>findByDishNameAndRestaurantId(UUID id, String name );
    /**
     *  znajduje dania po id restauracji
     */
    @Query("SELECT d FROM Dish d WHERE d.restaurantId = ?1")
    Iterable<Dish> findByRestaurantId(UUID id);
    /**
     *  znajduje dania po id restauracji, i dzieli je na strony
     */
    @Query(value = "SELECT d FROM Dish d WHERE d.restaurantId = ?1")
    Page<Dish> findDishesByRestaurantName(UUID restaurantId, Pageable pageable);
    int countDishesByRestaurantId(UUID restaurantId);

    Iterable<Dish> findDishesByRestaurantName(String name);

}
