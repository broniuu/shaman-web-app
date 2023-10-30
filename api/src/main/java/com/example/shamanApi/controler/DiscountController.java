package com.example.shamanApi.controler;

import com.example.shamanApi.dto.DiscountDto;
import com.example.shamanApi.dto.DiscountToViewDto;
import com.example.shamanApi.dto.DishWithDiscountDto;
import com.example.shamanApi.dto.UnlockDiscountDto;
import com.example.shamanApi.repository.DiscountRepository;
import com.example.shamanApi.repository.UserRepository;
import com.example.shamanApi.service.DiscountService;
import com.example.shamanApi.service.IDiscountService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Klasa obsługuje endpointy związane zniżkami
 */
@RestController
public class DiscountController {
    private final IDiscountService discountService;
    public DiscountController(DiscountRepository discountRepository, UserRepository userRepository) {
        this.discountService = new DiscountService(discountRepository, userRepository);
    }

    /**
     * Zwraca listę zniżek
     *
     * @param pageNumber    numer obecnej strony
     * @return              lista zniżek
     */
    @GetMapping(value = "/discounts",params = {"p"})
    public ResponseEntity<Page<DiscountToViewDto>> getDiscounts(
            @RequestParam(value = "p", defaultValue = "0") int pageNumber){
        Page<DiscountToViewDto> discountDto = discountService.findDiscountDtos(pageNumber);
        return new ResponseEntity<>(discountDto, HttpStatus.OK);
    }

    @PostMapping(value = "/discounts/unlockDiscount")
    public ResponseEntity<DishWithDiscountDto> unlockDiscount(
            @RequestBody UnlockDiscountDto unlockDiscountDto) {
        DishWithDiscountDto dishWithDiscountDto = discountService.tryUnlockDiscount(
                unlockDiscountDto.getUnlockCode(), unlockDiscountDto.getUserLogin());
        return new ResponseEntity<>(dishWithDiscountDto, HttpStatus.OK);
    }

    @GetMapping("/{login}/discounts")
    public ResponseEntity<Iterable<DiscountDto>> getDiscountsByOwnersLogin(@PathVariable String login) {
        Iterable<DiscountDto> discountDtos = discountService.getDiscountsOfUser(login);
        return new ResponseEntity<>(discountDtos,HttpStatus.OK);
    }
}
