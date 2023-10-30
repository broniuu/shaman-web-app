package com.example.shamanApi.controler;


import com.example.shamanApi.dto.RestaurantDto;
import com.example.shamanApi.repository.RestaurantRepository;
import com.example.shamanApi.service.DishService;
import com.example.shamanApi.service.IRestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Klasa obsługuje endpointy związane z restauracjami
 */
@RestController
public class RestaurantsController {

    private final IRestaurantService restaurantsService;
    private final RestaurantRepository restaurantRepository;
    private final com.example.shamanApi.service.DishService dishService;
    public RestaurantsController(IRestaurantService restaurantsService, RestaurantRepository restaurantRepository, DishService dishService) {

        this.restaurantsService = restaurantsService;
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
    }

    /**
     * Zwraca listę restauracji
     *
     * @return      lista restauracji
     */
    @GetMapping("/restaurants")

    public ResponseEntity<Iterable<RestaurantDto>> getRestaurants(){

        return new ResponseEntity(restaurantsService.GetAllRestaurants(), HttpStatus.OK);
    }

    /**
     * Wyświetla konkretną restauracje
     *
     * @param restaurantName    nazwa restauracji do wyświetlenia
     * @return                  restauracja
     */
    @GetMapping("/restaurants/{restaurantName}")
    public ResponseEntity<RestaurantDto> getRestaurantByName( @PathVariable String restaurantName){
         restaurantName= restaurantName.replaceAll("_"," ");
         RestaurantDto restaurantDto=restaurantsService.findRestaurantByName(restaurantName);

        return new ResponseEntity(restaurantDto, HttpStatus.OK);
    }

}
