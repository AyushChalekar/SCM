package com.deliverytracker.services;

import com.deliverytracker.DatabaseConnection;
import com.deliverytracker.models.Order;
import java.sql.*;

public class OrderService {

    private Connection connection;

    public OrderService() {
        connection = DatabaseConnection.getConnection();
    }

    // Method to create a new order
    public boolean createOrder(int userId, String trackingId, String status) {
        String query = "INSERT INTO orders (user_id, tracking_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, trackingId);
            ps.setString(3, status);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get the status of an order by tracking ID
    public String getOrderStatus(String trackingId) {
        String query = "SELECT status FROM orders WHERE tracking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, trackingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update the status of an order by tracking ID
    public boolean updateOrderStatus(String trackingId, String status) {
        String query = "UPDATE orders SET status = ? WHERE tracking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, trackingId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
