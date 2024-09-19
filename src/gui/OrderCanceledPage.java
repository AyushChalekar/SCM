package com.wms.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.wms.utils.DatabaseConnection;

public class OrderCanceledPage extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public OrderCanceledPage() {
        setLayout(null);

        // Create a "Refresh" button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(20, 20, 100, 30);
        add(refreshButton);

        // Table to display canceled orders
        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Product Name", "Category", "Quantity"}, 0);
        table = new JTable(tableModel);
        table.setBounds(20, 60, 600, 400);
        add(table);

        // Action listener for refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCanceledOrders();
            }
        });
    }

    // Method to refresh the list of canceled orders
    public void refreshCanceledOrders() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "SELECT o.order_id, p.product_name, c.category_name, p.quantity " +
                "FROM orders o " +
                "JOIN products p ON o.product_id = p.product_id " +
                "LEFT JOIN category c ON p.category_id = c.category_id " +
                "WHERE o.status = 'canceled'";  // Ensure the 'status' column exists in your 'orders' table

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0);  // Clear previous rows

            // Populate table with data
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String productName = rs.getString("product_name");
                String category = rs.getString("category_name");
                int quantity = rs.getInt("quantity");

                tableModel.addRow(new Object[]{orderId, productName, category, quantity});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
