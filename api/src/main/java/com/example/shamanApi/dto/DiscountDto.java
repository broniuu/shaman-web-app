package com.example.shamanApi.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DiscountDto {
    private UUID discountId;
    private UUID dishId;
    private double discountValue;
    private String discountCode;
    private LocalDate expireDate;
    private DishDto dish;
    private List<UserDto> usersWhoUsedThisDiscount;

    public UUID getDiscountId() {
        return discountId;
    }

    public void setDiscountId(UUID discountId) {
        this.discountId = discountId;
    }

    public UUID getDishId() {
        return dishId;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public DishDto getDish() {
        return dish;
    }

    public void setDish(DishDto dish) {
        this.dish = dish;
    }

    public List<UserDto> getUsersWhoUsedThisDiscount() {
        return usersWhoUsedThisDiscount;
    }

    public void setUsersWhoUsedThisDiscount(List<UserDto> usersWhoUsedThisDiscount) {
        this.usersWhoUsedThisDiscount = usersWhoUsedThisDiscount;
    }
}
