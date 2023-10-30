package com.example.shamanApi.service;

import com.example.shamanApi.dto.DishDto;
import org.springframework.data.domain.Page;

import java.util.UUID;
/**
 * Interfejs do obsługi klasy Dish
 */
public interface IDishService {

    DishDto findByDishNameAndRestaurantId( UUID id,String name);
    Iterable<DishDto> findByRestaurantId(UUID id);
    Page<DishDto> findPagedDishesByRestaurantName(String restaurantName, int pageNumber);

    Iterable<DishDto> findByRestaurantName(String restaurantName);
}
