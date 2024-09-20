package com.wms.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ShipmentManagementFrame extends JFrame {

    public ShipmentManagementFrame(String username) {
        setTitle("Shipment Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBackground(new Color(40, 40, 40));

        // Track Incoming Shipments
        JButton trackShipmentButton = new JButton("Track Incoming Shipments");
        trackShipmentButton.setBackground(new Color(70, 70, 70));
        trackShipmentButton.setForeground(Color.WHITE);

        // Place New Shipment
        JButton newShipmentButton = new JButton("Place New Shipment");
        newShipmentButton.setBackground(new Color(70, 70, 70));
        newShipmentButton.setForeground(Color.WHITE);

        // Back and Logout buttons
        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        // Add buttons to the panel
        mainPanel.add(trackShipmentButton);
        mainPanel.add(newShipmentButton);
        mainPanel.add(bottomPanel);

        add(mainPanel);

        // Action listeners
        trackShipmentButton.addActionListener(e -> {
            // Logic to track incoming shipments (go to tracking page)
            new TrackShipmentFrame(username);
        });

        newShipmentButton.addActionListener(e -> {
            // Logic to place a new shipment
            new NewShipmentFrame(username);
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
           // new OrderManagementFrame(username);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
