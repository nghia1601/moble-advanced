package com.cgm.hello_android_app_k15pm06.CartCheckout;

import com.google.gson.annotations.SerializedName;

public class CartItemInfo {

    @SerializedName("title")
    private String title;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    public CartItemInfo(String title, int quantity, double price) {
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
