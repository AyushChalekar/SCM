package com.deliverytracker.gui;

import com.deliverytracker.services.OrderService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {

    private OrderService orderService;
    private JLabel createOrderLabel;
    private JLabel trackOrderLabel;
    private JTextField createTrackingIdField;
    private JTextField createStatusField;
    private JTextField createUserIdField;
    private JTextField trackTrackingIdField;
    private JTextArea orderStatusArea;
    private JButton createOrderButton;
    private JButton viewStatusButton;
    private JButton logoutButton;
    private JButton tableButton;

    public AdminFrame() {
        orderService = new OrderService(); // Initialize OrderService instance

        setTitle("Admin Dashboard");
        setSize(800, 600); // You can adjust the width (800) and height (600) as needed

        // Optionally, to start maximized:
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Ensure the frame appears at the center of the screen
        setLocationRelativeTo(null);

        // Make the frame visible
        setVisible(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Create New Order Section
        createOrderLabel = new JLabel("Create New Order:");
        createTrackingIdField = new JTextField(15);
        createStatusField = new JTextField(15);
        createUserIdField = new JTextField(15);
        createOrderButton = new JButton("Create Order");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createOrderLabel, gbc);

        gbc.gridx = 1;
        add(new JLabel("Tracking ID:"), gbc);

        gbc.gridx = 2;
        add(createTrackingIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("Status:"), gbc);

        gbc.gridx = 2;
        add(createStatusField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(new JLabel("User ID:"), gbc);

        gbc.gridx = 2;
        add(createUserIdField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        add(createOrderButton, gbc);

        // Track Order Section
        trackOrderLabel = new JLabel("Track Order:");
        trackTrackingIdField = new JTextField(15);
        viewStatusButton = new JButton("View Status");
        orderStatusArea = new JTextArea(10, 30);
        orderStatusArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(trackOrderLabel, gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Tracking ID:"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;

        add(trackTrackingIdField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;

        add(viewStatusButton, gbc);

        gbc.gridy = 7;
        gbc.gridwidth = 3;
        add(new JScrollPane(orderStatusArea), gbc);

        // Logout Button
        logoutButton = new JButton("Logout");
        gbc.gridy = 8;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(logoutButton, gbc);



        // Action Listeners
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackingId = createTrackingIdField.getText();
                String status = createStatusField.getText();
                int userId;
                try {
                    userId = Integer.parseInt(createUserIdField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid User ID.");
                    return;
                }
                if (orderService.createOrder(userId, trackingId, status)) {
                    JOptionPane.showMessageDialog(null, "Order created successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating order.");
                }
            }
        });

        viewStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackingId = trackTrackingIdField.getText();
                String status = orderService.getOrderStatus(trackingId);
                if (status != null) {
                    orderStatusArea.setText("Status for " + trackingId + ": " + status);
                } else {
                    orderStatusArea.setText("Order not found.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(); // Redirect to login frame
                dispose(); // Close the admin frame
            }

        });



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
