package com.wms.models;

public class Product {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private float price;
    private String description;

    public Product(int id, String name, String category, int quantity, float price, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
