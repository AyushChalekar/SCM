package com.wms.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TrackShipmentFrame extends JFrame {

    public TrackShipmentFrame(String username) {
        setTitle("Track Shipment");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBackground(new Color(40, 40, 40));

        JLabel shipmentIdLabel = new JLabel("Enter Shipment ID:");
        shipmentIdLabel.setForeground(Color.WHITE);
        JTextField shipmentIdField = new JTextField();

        JButton trackButton = new JButton("Track Shipment");
        trackButton.setBackground(new Color(70, 70, 70));
        trackButton.setForeground(Color.WHITE);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setForeground(Color.WHITE);
        JLabel productNameValueLabel = new JLabel();
        productNameValueLabel.setForeground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        JLabel categoryValueLabel = new JLabel();
        categoryValueLabel.setForeground(Color.WHITE);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        JLabel quantityValueLabel = new JLabel();
        quantityValueLabel.setForeground(Color.WHITE);

        // Back and Logout buttons
        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        // Add components to the panel
        mainPanel.add(shipmentIdLabel);
        mainPanel.add(shipmentIdField);
        mainPanel.add(new JLabel());  // Spacer
        mainPanel.add(trackButton);
        mainPanel.add(productNameLabel);
        mainPanel.add(productNameValueLabel);
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryValueLabel);
        mainPanel.add(quantityLabel);
        mainPanel.add(quantityValueLabel);
        mainPanel.add(bottomPanel);

        add(mainPanel);

        // Action listeners
        trackButton.addActionListener(e -> {
            String shipmentId = shipmentIdField.getText();
            // Logic to track shipment details from the database
            String[] shipmentDetails = getShipmentDetails(shipmentId);  // Placeholder function

            if (shipmentDetails != null) {
                productNameValueLabel.setText(shipmentDetails[0]);
                categoryValueLabel.setText(shipmentDetails[1]);
                quantityValueLabel.setText(shipmentDetails[2]);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Shipment ID.");
            }
        });

        logoutButton.addActionListener(e -> {
            dispose();
            try {
                new LoginFrame();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new ShipmentManagementFrame(username);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private String[] getShipmentDetails(String shipmentId) {
        // Simulate fetching shipment details from the database
        return new String[]{"Product A", "Category X", "100"};  // Placeholder data
    }
}
