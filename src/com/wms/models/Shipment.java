package com.wms.models;

public class Shipment {
    private int id;
    private int productId;
    private int quantity;
    private String location;

    public Shipment(int id, int productId, int quantity, String location) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }
}
