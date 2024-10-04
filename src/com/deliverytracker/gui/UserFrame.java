package com.deliverytracker.gui;

import com.deliverytracker.services.OrderService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame {

    private OrderService orderService;
    private JLabel trackOrderLabel;
    private JTextField trackingIdField;
    private JTextArea orderStatusArea;
    private JButton viewStatusButton;
    private JButton logoutButton;

    public UserFrame() {
        orderService = new OrderService(); // Initialize OrderService instance

        setTitle("User Dashboard");
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

        trackOrderLabel = new JLabel("Track Your Order:");
        trackingIdField = new JTextField(15);
        viewStatusButton = new JButton("View Status");
        orderStatusArea = new JTextArea(10, 30);
        orderStatusArea.setEditable(false);
        logoutButton = new JButton("Logout");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(trackOrderLabel, gbc);

        gbc.gridx = 1;
        add(new JLabel("Tracking ID:"), gbc);

        gbc.gridx = 2;
        add(trackingIdField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        add(viewStatusButton, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(new JScrollPane(orderStatusArea), gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(logoutButton, gbc);

        viewStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackingId = trackingIdField.getText();
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
                dispose(); // Close the user frame
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
