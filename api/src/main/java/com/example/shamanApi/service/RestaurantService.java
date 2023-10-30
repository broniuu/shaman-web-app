package com.example.shamanApi.service;

import com.example.shamanApi.dto.RestaurantDto;
import com.example.shamanApi.exception.RestarurantNotFoundException;
import com.example.shamanApi.model.Restaurant;
import com.example.shamanApi.repository.RestaurantRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Obsługuje operacje związane z zarządzaniem restauracjami
 */
@Service
public class RestaurantService implements IRestaurantService{
    private final RestaurantRepository restaurantRepository;
    private final Mapper mapper;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    /**
     * Znajduje restauracje po nazwie
     *
     * @param name      nazwa restauracji
     * @return          restauracja
     */
    @Override
    public RestaurantDto findRestaurantByName(String name) {
        Restaurant restaurant= restaurantRepository.findByName(name).orElse(null);
        if(restaurant == null) throw new RestarurantNotFoundException("Did not found any Restaurants with name:"+name);
        RestaurantDto restaurantDto = new RestaurantDto();
        mapper.map(restaurant,restaurantDto);
        return restaurantDto;
    }

    /**
     * Zwraca wszystkie restauracje
     *
     * @return      restauracje
     */
    @Override
    public Iterable<RestaurantDto> GetAllRestaurants() {
        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
        if (restaurants.isEmpty()) throw new RestarurantNotFoundException("Did not found any Restaurants");
        List<RestaurantDto> restaurantDtos = new ArrayList<>();
        for(Restaurant restaurant : restaurants){
            RestaurantDto restaurantDto = mapper.map(restaurant, RestaurantDto.class);
            restaurantDtos.add(restaurantDto);
        }
        return restaurantDtos;
    }
}
