// Order.java
package com.wms.models;

import java.util.Date;

public class Order {
    private int orderId;
    private String customer;
    private String product;
    private int quantity;
    private String status;
    private Date orderDate;
    private Date estimatedDelivery;

    public Order(int orderId, int customer, int product, int quantity, String status, Date orderDate, Date estimatedDelivery) {
        this.orderId = orderId;
        this.customer = String.valueOf(customer);
        this.product = String.valueOf(product);
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
        this.estimatedDelivery = estimatedDelivery;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getEstimatedDelivery() {
        return estimatedDelivery;
    }
}
