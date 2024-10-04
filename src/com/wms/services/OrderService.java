package com.wms.services;

import com.wms.models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderService {
    private Connection conn;

    public OrderService(Connection conn) {
        this.conn = conn;
    }

    // Method to create an order
    public void createOrder(int customerId, int productId, int quantity) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(customerId));
            stmt.setString(2, String.valueOf(productId));
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }
    public class OrderResponse {
        private final int orderId;
        private final String trackingId;

        public OrderResponse(int orderId, String trackingId) {
            this.orderId = orderId;
            this.trackingId = trackingId;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getTrackingId() {
            return trackingId;
        }
    }


    // Method to get the order status by ID
    public Order getOrderStatusById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getString("status"),
                        rs.getDate("order_date"),
                        rs.getDate("estimated_delivery")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void placeOrder(int customerId, int productId, int quantity) {
        // Implementation
    }

    // Add other methods as needed, such as for updating or canceling orders
}
