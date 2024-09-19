package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.wms.utils.DatabaseConnection;

public class NewShipmentFrame extends JFrame {

    public NewShipmentFrame(String username) {
        setTitle("New Shipment Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBackground(new Color(40, 40, 40));

        // Shipment details
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setForeground(Color.WHITE);
        JTextField productNameField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        JTextField categoryField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        JTextField quantityField = new JTextField();

        JButton submitButton = new JButton("Submit Shipment");
        submitButton.setBackground(new Color(70, 70, 70));
        submitButton.setForeground(Color.WHITE);

        // Logout and Back buttons
        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        // Add components to the panel
        mainPanel.add(productNameLabel);
        mainPanel.add(productNameField);
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryField);
        mainPanel.add(quantityLabel);
        mainPanel.add(quantityField);
        mainPanel.add(new JLabel());  // Spacer
        mainPanel.add(submitButton);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        submitButton.addActionListener(e -> {
            String productName = productNameField.getText();
            String category = categoryField.getText();
            int quantity;

            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid quantity input.");
                return;
            }

            // Database logic to create new shipment
            boolean shipmentCreated = createNewShipmentInDatabase(username, productName, category, quantity);

            if (shipmentCreated) {
                JOptionPane.showMessageDialog(null, "Shipment created successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Shipment creation failed.");
            }
        });

        logoutButton.addActionListener(e -> {
            dispose();
            try {
                new LoginFrame();  // Navigate to login
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new ShipmentManagementFrame(username);  // Navigate back to shipment management
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private boolean createNewShipmentInDatabase(String username, String productName, String category, int quantity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertShipmentSQL = "INSERT INTO shipments (product_name, category, quantity, created_by) VALUES (?, ?, ?, ?)";

        try {
            connection = DatabaseConnection.getConnection();  // Get connection from DatabaseConnection utility
            preparedStatement = connection.prepareStatement(insertShipmentSQL);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, category);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setString(4, username);  // Assuming the username is stored in the `created_by` field

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;  // Return true if insertion is successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false in case of an error
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
