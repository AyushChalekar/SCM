package com.wms.ADMIN;

import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class UsersPage extends JFrame {
    private Connection connection;
    private UserData userData; // User details

    public UsersPage(Connection connection, UserData userData) {
        this.connection = connection;
        this.userData = userData; // Initialize userData
        setTitle("User Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // White background
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        JLabel titleLabel = new JLabel("User Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Large font for title
        titleLabel.setForeground(Color.BLACK); // Black text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1; // Add weight to give some space above the title
        getContentPane().add(titleLabel, gbc);

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.WHITE); // White background for panel
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.gridy = 1; // Move to the next row

        JButton btnCreateOrder = createStyledButton("Customer", "icons/create_order.png");
        JButton btnTrackOrder = createStyledButton("Delivery Partner", "icons/track_order.png");
        JButton btnOrderDelivered = createStyledButton("Suppliers", "icons/order_delivered.png");
        JButton btnOrderCanceled = createStyledButton("Employees", "icons/order_canceled.png");

        btnTrackOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeliverPartnerInteractionPage().setVisible(true);
            }
        });
        btnOrderCanceled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeInteractionPage().setVisible(true);
            }
        });
        btnOrderDelivered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new SupplierInteractionPage().setVisible(true);
            }
        });
        btnCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new CustomerInteractionPage().setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        navigationPanel.add(btnCreateOrder, gbc);

        gbc.gridy = 1;
        navigationPanel.add(btnTrackOrder, gbc);

        gbc.gridy = 2;
        navigationPanel.add(btnOrderDelivered, gbc);

        gbc.gridy = 3;
        navigationPanel.add(btnOrderCanceled, gbc);

        gbc.gridy = 2; // Adjust this to place navigation panel correctly
        getContentPane().add(navigationPanel, gbc);

        // Back button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        JButton btnBack = createStyledButton("Back ", "icons/back.png");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Users Page
                new AdminHomepage(connection, userData).setVisible(true); // Show the Admin Homepage
            }
        });
        getContentPane().add(btnBack, gbc);

        // Add margins to the buttons
        JButton[] buttons = {btnCreateOrder, btnTrackOrder, btnOrderDelivered, btnOrderCanceled, btnBack};
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(250, 60)); // Set button size
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Font size for buttons
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        if (iconPath != null) {
            button.setIcon(new ImageIcon(iconPath)); // Add icon to button
        }
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Font size for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Button color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Border color
        button.setPreferredSize(new Dimension(250, 60)); // Button size
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        return button;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection finalConnection = connection;
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
        SwingUtilities.invokeLater(() -> new UsersPage(finalConnection,userData).setVisible(true));
    }
}
