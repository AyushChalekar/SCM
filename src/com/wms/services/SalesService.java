package com.wms.services;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class SalesService {

    private Connection connection;

    public SalesService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel searchSales(String category, String searchText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Sale ID", "Product Name", "Category", "Amount"}, 0);
        JTable table = new JTable(model);

        String query = "SELECT * FROM sales WHERE (category = ? OR ? = 'All Categories') AND product_name LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, category);
            stmt.setString(3, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("sale_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("amount")
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
