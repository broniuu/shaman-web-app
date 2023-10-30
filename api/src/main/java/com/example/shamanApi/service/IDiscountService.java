package com.example.shamanApi.service;

import com.example.shamanApi.dto.CartItemDto;
import com.example.shamanApi.dto.DiscountDto;
import com.example.shamanApi.dto.DiscountToViewDto;
import com.example.shamanApi.dto.DishWithDiscountDto;
import com.example.shamanApi.model.Discount;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interfejs do obsługi klasy CartItem
 */
public interface IDiscountService {
    Page<DiscountToViewDto> findDiscountDtos(int pageNumber);
    DishWithDiscountDto tryUnlockDiscount(String discountCode, String login);
    Iterable<DiscountDto> getDiscountsOfUser(String userLogin);
    Discount saveUsedDiscountWithItsOwner(String discountCode, String ownersLogin, List<CartItemDto> cartItemDtos);
}
