package com.wms.ADMIN;

import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.SQLException;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShipmentPage extends JFrame {

    private Connection connection;
    private UserData userData; // User details

    public ShipmentPage(Connection connection, UserData userData) {
        this.connection = connection;
        this.userData = userData; // Initialize userData
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

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.WHITE); // Changed to white
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set margins for buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnCreateOrder = createStyledButton("Place Shipment", "icons/create_order.png");
        JButton btnTrackOrder = createStyledButton("Shipment Tracking", "icons/track_order.png");

        btnCreateOrder.addActionListener(e -> new PlaceShipmentPage(connection,userData).setVisible(true));
        btnTrackOrder.addActionListener(e -> new ShipmentTrackingPage(connection).setVisible(true));

        gbc.gridx = 0;
        gbc.gridy = 0;
        navigationPanel.add(btnCreateOrder, gbc);

        gbc.gridy = 1;
        navigationPanel.add(btnTrackOrder, gbc);

        // Add navigation panel to the center of the BorderLayout
        add(navigationPanel, BorderLayout.CENTER);

        // Back button at the bottom (BorderLayout.SOUTH)
        JButton btnBack = createStyledButton("Back to Admin Homepage", "icons/back_to_admin.png");
        btnBack.addActionListener(e -> {
            dispose(); // Close the Shipment Page
            new AdminHomepage(connection, userData).setVisible(true); // Show the Admin Homepage
        });
        add(btnBack, BorderLayout.SOUTH);

        // Add margins and size to buttons
        JButton[] buttons = {btnCreateOrder, btnTrackOrder, btnBack};
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(250, 60)); // Set button size
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Font size for buttons
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Font style
        button.setForeground(Color.white); // Text color
        button.setBackground(new Color(255, 110, 110)); // Orange background color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Load the icon from resources folder
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2), // Border
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding
        ));

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
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
            new ShipmentPage(connection,userData).setVisible(true);
        });
    }
}
