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

public class ProductManagementPage extends JPanel {
    private JTable productTable;
    private DefaultTableModel productTableModel;

    public ProductManagementPage() {
        setLayout(null);

        // Create a "Load Products" button
        JButton loadProductsButton = new JButton("Load Products");
        loadProductsButton.setBounds(20, 20, 150, 30);
        add(loadProductsButton);

        // Table to display products
        productTableModel = new DefaultTableModel(new Object[]{"Product ID", "Product Name", "Category", "Quantity"}, 0);
        productTable = new JTable(productTableModel);
        productTable.setBounds(20, 60, 600, 400);
        add(productTable);

        // Action listener for Load Products button
        loadProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });
    }

    // Method to load products into the table
    public void loadProducts() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "SELECT p.product_id, p.product_name, c.category_name, p.quantity " +
                "FROM products p " +
                "LEFT JOIN category c ON p.category_id = c.category_id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            productTableModel.setRowCount(0);  // Clear previous rows

            // Populate table with data
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                String category = rs.getString("category_name");
                int quantity = rs.getInt("quantity");

                productTableModel.addRow(new Object[]{productId, productName, category, quantity});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
