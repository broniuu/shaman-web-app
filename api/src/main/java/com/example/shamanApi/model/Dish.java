package com.example.shamanApi.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * Klasa reprezentuje danie
 */

@Entity
public class Dish {
    @Id
    private UUID dishId;
    private String name;
    private String description;
    private Double price;
    @Column(insertable = false, updatable = false)
    private UUID restaurantId;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurantId", nullable = false)
    private Restaurant restaurant;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "dishId", nullable = false)
    private CartItem cartItem;

    public Dish(UUID dishId, String name, String description, Double price, String imageUrl, Restaurant restaurant) {
        this.dishId = dishId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.restaurant = restaurant;
    }

    public Dish(String name, String description, Double price, String imageUrl, Restaurant restaurant) {
        this.dishId = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.restaurant = restaurant;
    }

    public Dish() {
        this.dishId = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Dish{" +
                "DishId=" + dishId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
