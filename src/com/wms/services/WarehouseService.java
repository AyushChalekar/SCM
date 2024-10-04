package com.wms.services;

import com.wms.models.Warehouse;
import com.wms.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WarehouseService {
    private Connection connection;

    public WarehouseService() {
        // conn = DatabaseConnection.getConnection();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root");
            // Perform SQL operations
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle exception for closing connection
                }
            }
        }

    }

    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT * FROM warehouses";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Warehouse warehouse = new Warehouse(
                        rs.getInt("warehouse_id"),
                        rs.getString("warehouse_name"),
                        rs.getString("location")
                );
                warehouses.add(warehouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouses;
    }

    public boolean createWarehouse(String warehouseName, String location) {
        String query = "INSERT INTO warehouses (warehouse_name, location) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, warehouseName);
            ps.setString(2, location);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
