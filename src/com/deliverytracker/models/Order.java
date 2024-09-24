package com.deliverytracker.models;

public class Order {
    private String trackingId;
    private int userId;
    private String status;

    public Order(String trackingId, int userId, String status) {
        this.trackingId = trackingId;
        this.userId = userId;
        this.status = status;
    }

    // Getters and setters
    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
