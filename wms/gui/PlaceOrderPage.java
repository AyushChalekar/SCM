package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PlaceOrderPage extends JFrame {
    private JTextField txtProductName;
    private JTextField txtQuantity;
    private int supplierId;

    public PlaceOrderPage(int supplierId) {
        this.supplierId = supplierId;
        setTitle("Place Order");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Form for placing an order
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Product Name:"));
        txtProductName = new JTextField();
        formPanel.add(txtProductName);
        formPanel.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        formPanel.add(txtQuantity);

        JButton btnPlaceOrder = new JButton("Place Order");
        btnPlaceOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });
        formPanel.add(btnPlaceOrder);

        add(formPanel, BorderLayout.CENTER);
    }

    private void placeOrder() {
        String productName = txtProductName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (product_name, quantity, supplier_id) VALUES (?, ?, ?)")) {
            stmt.setString(1, productName);
            stmt.setInt(2, quantity);
            stmt.setInt(3, supplierId);
            stmt.executeUpdate();
            txtProductName.setText("");
            txtQuantity.setText("");
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
