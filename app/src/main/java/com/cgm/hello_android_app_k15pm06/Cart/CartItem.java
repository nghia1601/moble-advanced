package com.cgm.hello_android_app_k15pm06.Cart;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    private int id;
    private int productId;
    private String title;
    private int quantity;
    private double price;
    private String image;


    public CartItem(int id, int productId, String title, int quantity, double price, String image) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    // Thêm các phương thức getter và setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
