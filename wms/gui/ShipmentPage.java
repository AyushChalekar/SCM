package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ShipmentPage extends JFrame {

    private Connection connection;

    public ShipmentPage(Connection connection) {
        this.connection = connection;
        setTitle("Shipment Page");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridLayout(2, 1));
        JButton btnPlaceShipment = new JButton("Place Shipment");
        JButton btnShipmentTracking = new JButton("Shipment Tracking");

        btnPlaceShipment.addActionListener(e -> new PlaceShipmentPage(connection).setVisible(true));

        btnShipmentTracking.addActionListener(e -> new ShipmentTrackingPage(connection).setVisible(true));

        navigationPanel.add(btnPlaceShipment);
        navigationPanel.add(btnShipmentTracking);

        add(navigationPanel, BorderLayout.CENTER);

        // Back button
        JButton btnBack = new JButton("Back to Admin Homepage");
        btnBack.addActionListener(e -> {
            dispose(); // Close the Shipment Page
            new AdminHomepage(connection).setVisible(true); // Show the Admin Homepage
        });
        add(btnBack, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection finalConnection = connection;
        SwingUtilities.invokeLater(() -> new ShipmentPage(finalConnection).setVisible(true));
    }
}
