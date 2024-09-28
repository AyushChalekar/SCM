package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShipmentPage extends JFrame {

    private Connection connection;

    public ShipmentPage(Connection connection) {
        this.connection = connection;
        setTitle("Shipment Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // White background

        // Title Label setup
        JLabel titleLabel = new JLabel("Shipment Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Font style for title
        titleLabel.setForeground(new Color(255, 110, 110)); // Orange title color
        add(titleLabel, BorderLayout.NORTH); // Add title at the top

        // Navigation Panel setup
        JPanel navigationPanel = new JPanel(new GridLayout(2, 1, 100, 100)); // Grid layout with spacing
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setBorder(new EmptyBorder(250, 250, 250, 250)); // Padding

        // Place Shipment button
        JButton btnPlaceShipment = createStyledButton("Place Shipment", "icons/place.png");
        btnPlaceShipment.addActionListener(e -> new PlaceShipmentPage(connection).setVisible(true));
        navigationPanel.add(btnPlaceShipment);

        // Shipment Tracking button
        JButton btnShipmentTracking = createStyledButton("Shipment Tracking", "icons/track.png");
        btnShipmentTracking.addActionListener(e -> new ShipmentTrackingPage(connection).setVisible(true));
        navigationPanel.add(btnShipmentTracking);

        add(navigationPanel, BorderLayout.CENTER);

        // Back button setup
        JButton btnBack = createStyledButton("Back to Admin Homepage", "icons/back.png");
        btnBack.addActionListener(e -> {
            dispose(); // Close the Shipment Page
            new AdminHomepage(connection).setVisible(true); // Show the Admin Homepage
        });
        add(btnBack, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Font style
        button.setForeground(Color.BLACK); // Text color
        button.setBackground(new Color(255, 110, 110)); // Orange background color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2), // Border
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding
        ));
        button.setPreferredSize(new Dimension(10, 30)); // Minimum size

        // Button animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Revert to orange background
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection connection = null;
            try {
                connection = DatabaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new ShipmentPage(connection).setVisible(true);
        });
    }
}
