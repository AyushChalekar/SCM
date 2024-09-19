package com.wms.services;

import com.wms.models.Shipment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentService {
    private Connection conn;

    public ShipmentService(Connection conn) {
        this.conn = conn;
    }

    public void addShipment(Shipment shipment) throws SQLException {
        String sql = "INSERT INTO shipments (product_id, quantity, location) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shipment.getProductId());
            stmt.setInt(2, shipment.getQuantity());
            stmt.setString(3, shipment.getLocation());
            stmt.executeUpdate();
        }
    }

    public List<Shipment> getShipments() throws SQLException {
        List<Shipment> shipments = new ArrayList<>();
        String sql = "SELECT * FROM shipments";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Shipment shipment = new Shipment(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getString("location")
                );
                shipments.add(shipment);
            }
        }
        return shipments;
    }

    public void deleteShipment(int id) throws SQLException {
        String sql = "DELETE FROM shipments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
