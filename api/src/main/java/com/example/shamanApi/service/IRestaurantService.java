package com.example.shamanApi.service;

import com.example.shamanApi.dto.RestaurantDto;

/**
 * Interfejs do obsługi klasy Restaurant
 */
public interface IRestaurantService {
    RestaurantDto findRestaurantByName(String name);
    Iterable<RestaurantDto> GetAllRestaurants();

}
