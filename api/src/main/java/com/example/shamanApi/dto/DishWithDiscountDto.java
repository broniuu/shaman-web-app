package com.example.shamanApi.dto;

import java.text.DecimalFormat;
import java.util.UUID;

public class DishWithDiscountDto {
    private UUID dishId;
    private String name;
    private String oldPrice;
    private String newPrice;
    private String percentageDiscount;
    private String savedMoney;

    public void setPercentageDiscount(String percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public String getSavedMoney() {
        return savedMoney;
    }

    public void setSavedMoney(String savedMoney) {
        this.savedMoney = savedMoney;
    }
    public UUID getDishId() {
        return dishId;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {

        this.newPrice = newPrice;
    }

    public String getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(double discountValue) {
        double discountConvertedToPercent = discountValue * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0");
        String discountPercentText = decimalFormat.format(discountConvertedToPercent);
        this.percentageDiscount = discountPercentText + " %";
    }
}
