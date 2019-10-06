package com.example.myapplication;

public class Items {
    private String name,url,price;

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

    public Items(String name, String url, String price) {
        this.name = name;
        this.url = url;
        this.price = price;
    }
}
