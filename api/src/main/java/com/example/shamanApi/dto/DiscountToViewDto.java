package com.example.shamanApi.dto;
import lombok.Data;

@Data
public class DiscountToViewDto {
    private String dishName;
    private String discountAsPercentages;

    public DiscountToViewDto(String dishName, String discountAsPercentages) {
        this.dishName = dishName;
        this.discountAsPercentages = discountAsPercentages;
    }
}
