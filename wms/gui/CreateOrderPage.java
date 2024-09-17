package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateOrderPage extends JFrame {

    private Connection connection;

    public CreateOrderPage(Connection connection) {
        this.connection = connection;
        setTitle("Create Order Page");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Form for creating orders
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Product Name:"));
        JTextField txtProductName = new JTextField();
        formPanel.add(txtProductName);

        formPanel.add(new JLabel("Quantity:"));
        JTextField txtQuantity = new JTextField();
        formPanel.add(txtQuantity);

        JButton btnCreateOrder = new JButton("Create Order");
        btnCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to create order
                createOrder(txtProductName.getText(), Integer.parseInt(txtQuantity.getText()));
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(btnCreateOrder, BorderLayout.SOUTH);

        // Back button
        JButton btnBack = new JButton("Back to Order Page");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Create Order Page
                new OrderPage(connection).setVisible(true); // Show the Order Page
            }
        });
        add(btnBack, BorderLayout.NORTH);
    }

    private void createOrder(String productName, int quantity) {
        // Implement the logic to create an order
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection finalConnection = connection;
        SwingUtilities.invokeLater(() -> new CreateOrderPage(finalConnection).setVisible(true));
    }
}
