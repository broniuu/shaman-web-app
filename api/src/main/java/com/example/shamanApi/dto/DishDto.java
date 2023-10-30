package com.example.shamanApi.dto;

import java.util.UUID;
/**
 * klasa przechowuje informacje używane przy wymianie informacij (Data transfer object) dla dania.
 */
public class DishDto {
    private UUID dishId;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
