package com.example.myapplication;

public class Items {
    private String name,url,price,category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Items(String name, String url, String price, String category) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.category = category;
    }
}
