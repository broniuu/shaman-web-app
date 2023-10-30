package com.example.shamanApi.controler;

import com.example.shamanApi.dto.DishDto;
import com.example.shamanApi.repository.RestaurantRepository;
import com.example.shamanApi.service.DishService;
import com.example.shamanApi.service.IRestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
/**
 * Klasa obsługuje endpointy związane z daniami.
 */
@RestController
public class DishController {
    private final IRestaurantService restaurantsService;
    private final RestaurantRepository restaurantRepository;
    private final com.example.shamanApi.service.DishService dishService;

    public DishController(IRestaurantService restaurantsService, RestaurantRepository restaurantRepository, DishService dishService) {
        this.restaurantsService = restaurantsService;
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
    }
    /**
     * zwraca liste dan dla danej restauracij podzielonej na strony
     *
     * @param restaurantName    nazwa restauracji
     * @param pageNumber    numer strony
     * */
    @GetMapping(value = "/restaurants/{restaurantName}/dishes", params = {"p"})
    public ResponseEntity<Iterable<DishDto>> getPagedDishesByRestaurantName(
            @PathVariable String restaurantName,
            @RequestParam(value = "p", defaultValue = "0") int pageNumber){

        restaurantName= restaurantName.replaceAll("_"," ");
        Page<DishDto> dishDtos = dishService.findPagedDishesByRestaurantName(restaurantName, pageNumber);
        return new ResponseEntity(dishDtos, HttpStatus.OK);
    }
    /**
     * zwraca pojeduncze danie po nazwie dania i restauracij
     *
     * @param dishName      nazwa dania
     * @param restaurantName    nazwa restauracij
     * */
    @GetMapping("/restaurants/{restaurantName}/dishes/{dishName}")
    public ResponseEntity<DishDto> getDishByRestaurantName(
            @PathVariable String restaurantName,
            @PathVariable String dishName){
        restaurantName= restaurantName.replaceAll("_"," ");
        dishName=dishName.replaceAll("_"," ");
        UUID id=restaurantsService.findRestaurantByName(restaurantName).getRestaurantId();
        return new ResponseEntity(dishService.findByDishNameAndRestaurantId(id,dishName), HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurantName}/dishes")
    public ResponseEntity<Iterable<DishDto>> getDishesByRestaurantName(@PathVariable String restaurantName) {
        String realRestaurantName = restaurantName.replaceAll("_"," ");
        Iterable<DishDto> dishDtos = dishService.findByRestaurantName(realRestaurantName);
        return new ResponseEntity<>(dishDtos, HttpStatus.OK);
    }
}
