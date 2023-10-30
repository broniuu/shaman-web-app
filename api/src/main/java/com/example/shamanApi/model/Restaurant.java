package com.example.shamanApi.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Klasa reprezentuje restaurację
 */

@Entity
public class Restaurant {
    @Id
    private UUID restaurantId;
    private String name;
    private String imageUrl;
    private String backgroundImageUrl;
    private int score;
    private String street;
    private String houseNumber;
    private String location;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Dish> dishes;

    public Restaurant(String name, String imageUrl, String backgroundImageUrl, int score, String street, String houseNumber, String location) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.score = score;
        this.street = street;
        this.houseNumber = houseNumber;
        this.location = location;
        this.restaurantId = UUID.randomUUID();
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public Restaurant() {
        this.restaurantId = UUID.randomUUID();
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
