package com.wms.services;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class OrderTrackingService {

    private Connection connection;

    public OrderTrackingService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel searchOrderTracking(String category, String searchText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Order ID", "Status", "Tracking Number"}, 0);
        JTable table = new JTable(model);

        String query = "SELECT * FROM order_tracking WHERE (category = ? OR ? = 'All Categories') AND status LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, category);
            stmt.setString(3, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("status"),
                        rs.getString("tracking_number")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
