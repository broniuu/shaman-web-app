package com.example.shamanApi.dto;

public class CheckoutDto {
    private String discountCode;
    private String note;
    private boolean delivery;

    public boolean getDelivery() {
        return delivery;
    }
    public String getNote() {
        return note;
    }
    public String getDiscountCode() {
        return discountCode;
    }
}
