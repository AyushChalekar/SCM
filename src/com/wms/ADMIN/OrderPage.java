package com.wms.ADMIN;

import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderPage extends JFrame {
    private Connection connection;
    private UserData userData; // Declare UserData

    public OrderPage(Connection connection, UserData userData) {
        this.connection = connection;
        this.userData = userData; // Initialize UserData
        setTitle("Order Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Changed to white
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        JLabel titleLabel = new JLabel("Order Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Font size for title
        titleLabel.setForeground(new Color(255, 110, 110)); // Orange color for title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center the title
        add(titleLabel, gbc);

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.WHITE); // Changed to white
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 1; // Adjusted y position for navigation panel

        JButton btnCreateOrder = createStyledButton("Create Order", "icons/create_order.png");
        JButton btnTrackOrder = createStyledButton("Track Order", "icons/track_order.png");
        JButton btnOrderDelivered = createStyledButton("Order Delivered", "icons/order_delivered.png");
        JButton btnOrderCanceled = createStyledButton("Order Canceled", "icons/order_canceled.png");

        btnCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderCreationPage().setVisible(true);
            }
        });

        btnTrackOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderTrackingPage(connection).setVisible(true);
            }
        });

        btnOrderCanceled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderCanceledPage().setVisible(true);
            }
        });

        btnOrderDelivered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderDeliveredPage().setVisible(true);
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

        add(navigationPanel, gbc);

        // Back button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        JButton btnBack = createStyledButton("Back to Admin Homepage", "icons/back_to_admin.png");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Order Page
                new AdminHomepage(connection, userData).setVisible(true); // Show Admin Homepage with UserData
            }
        });
        add(btnBack, gbc);

        // Add margins to the buttons
        JButton[] buttons = {btnCreateOrder, btnTrackOrder, btnOrderDelivered, btnOrderCanceled, btnBack};
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(250, 60)); // Set button size
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Font size for buttons
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setBackground(new Color(255, 110, 110)); // Changed to orange
        button.setForeground(Color.white); // Changed text to black
        button.setFont(new Font("Arial", Font.BOLD, 24)); // Large font size
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Border
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        Connection connection = null;
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
        try {
            connection = DatabaseConnection.getConnection();
            // Initialize UserData here if needed
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection finalConnection = connection;
        SwingUtilities.invokeLater(() -> new OrderPage(finalConnection, userData).setVisible(true));
    }
}
