package com.wms.services;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CustomerService {

    private Connection connection;

    public CustomerService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel searchCustomers(String category, String searchText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Customer ID", "Name", "Category", "Contact"}, 0);
        JTable table = new JTable(model);

        String query = "SELECT * FROM customers WHERE (category = ? OR ? = 'All Categories') AND name LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, category);
            stmt.setString(3, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("contact")
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
