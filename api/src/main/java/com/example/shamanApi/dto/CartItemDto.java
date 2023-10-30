package com.example.shamanApi.dto;

import java.util.UUID;

public class CartItemDto {
    private UUID cartItemId;
    private int countOfDish;
    private CartOwnerDto cartOwner;
    private DishDto dish;

    public UUID getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCountOfDish() {
        return countOfDish;
    }

    public void setCountOfDish(int countOfDish) {
        this.countOfDish = countOfDish;
    }

    public CartOwnerDto getCartOwner() {
        return cartOwner;
    }

    public void setCartOwner(CartOwnerDto cartOwner) {
        this.cartOwner = cartOwner;
    }

    public DishDto getDish() {
        return dish;
    }

    public void setDish(DishDto dish) {
        this.dish = dish;
    }
}
