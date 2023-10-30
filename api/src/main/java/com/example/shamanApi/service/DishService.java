package com.example.shamanApi.service;

import com.example.shamanApi.dto.DishDto;
import com.example.shamanApi.exception.DishNotFoundException;
import com.example.shamanApi.model.Dish;
import com.example.shamanApi.repository.DishRepository;
import com.example.shamanApi.repository.RestaurantRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Obsługuje operacje związane z zarządzaniem potrawami
 */
@Service
public class DishService implements IDishService {
    private final DishRepository dishRepository;
    private final Mapper mapper;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();

    }

    /**
     * Znajduje danie po Nazwie dania, Oraz ID restauracij
     *
     * @param id - id restauracij
     * @param name nazwa dania
     * @return Zwraca pojedyńczy obiekt DTO dla dania.
     */
    @Override
    public DishDto findByDishNameAndRestaurantId( UUID id,String name) {
        Dish dish= dishRepository.findByDishNameAndRestaurantId(id,name)
                .orElse(null);
        if(dish==null) throw new DishNotFoundException("Dish:"+name+" not Found in Restaurant");
        DishDto dishDto = new DishDto();

        mapper.map(dish,dishDto);
        return dishDto;
    }
    /**
     * Znajduje dania po ID restauracij
     *
     * @param id - id restauracij\
     * @return liste obiektów DTO wszystkich dań z danej restauracij.
     */
    @Override
    public Iterable<DishDto> findByRestaurantId(UUID id) {
        List<Dish> dishes = (List<Dish>) dishRepository.findByRestaurantId(id);
        return convertToDishDtos(dishes);
    }
    /**
     * Znajduje dania po nazwie restauracij
     *
     * @param restaurantName - nazwa restauracij
     * @param pageNumber - numer strony
     * @return Zwraca liste Dam podzielonych na strony.
     */
    @Override
    public Page<DishDto> findPagedDishesByRestaurantName(String restaurantName, int pageNumber) {
        final int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UUID restaurantId = restaurantRepository.findRestaurantIdByName(restaurantName);
        int maxCountOfDishes = dishRepository.countDishesByRestaurantId(restaurantId);
        Page<Dish> pagedDishes = dishRepository.findDishesByRestaurantName(restaurantId, pageable);
        if (pagedDishes.isEmpty() || pagedDishes.getTotalElements() > maxCountOfDishes)
            throw new DishNotFoundException("There is no Dishes for this Restaurant on this page");
        return pagedDishes.map(dish -> mapper.map(dish, DishDto.class));
    }

    @Override
    public Iterable<DishDto> findByRestaurantName(String restaurantName){
        List<Dish> dishes = (List<Dish>) dishRepository.findDishesByRestaurantName(restaurantName);
        return convertToDishDtos(dishes);
    }

    private Iterable<DishDto> convertToDishDtos(List<Dish> dishes) {
        if (dishes.isEmpty()) throw new DishNotFoundException("There is no Dishes for this Restaurant. Your Restaurant ID may be wrong");
        List<DishDto> DishDtos = new ArrayList<>();
        for(Dish dish : dishes){
            DishDto dishDto = mapper.map(dish, DishDto.class);
            DishDtos.add(dishDto);
        }
        return DishDtos;
    }
}
