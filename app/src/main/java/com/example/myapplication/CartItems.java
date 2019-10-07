package com.example.myapplication;

public class CartItems {
    private String image,name,quantity,price;
    public CartItems(String image, String name, String quantity, String price) {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}
