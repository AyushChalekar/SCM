package com.deliverytracker.gui;

import com.deliverytracker.services.OrderService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeliveryPartnerFrame extends JFrame {

    private OrderService orderService;
    private JLabel updateOrderLabel;
    private JTextField trackingIdField;
    private JComboBox<String> statusComboBox;
    private JButton updateStatusButton;
    private JButton logoutButton;

    public DeliveryPartnerFrame() {
        orderService = new OrderService(); // Initialize OrderService instance

        setTitle("Delivery Partner Dashboard");
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

        updateOrderLabel = new JLabel("Update Order Status:");
        trackingIdField = new JTextField(15);
        String[] statuses = {"In Transit", "Out for Delivery", "Delivered"};
        statusComboBox = new JComboBox<>(statuses);
        updateStatusButton = new JButton("Update Status");
        logoutButton = new JButton("Logout");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(updateOrderLabel, gbc);

        gbc.gridx = 1;
        add(new JLabel("Tracking ID:"), gbc);

        gbc.gridx = 2;
        add(trackingIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("Status:"), gbc);

        gbc.gridx = 2;
        add(statusComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(updateStatusButton, gbc);

        gbc.gridy = 3;
        add(logoutButton, gbc);

        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackingId = trackingIdField.getText();
                String status = (String) statusComboBox.getSelectedItem();
                if (orderService.updateOrderStatus(trackingId, status)) {
                    JOptionPane.showMessageDialog(null, "Order status updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating order status.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(); // Redirect to login frame
                dispose(); // Close the delivery partner frame
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
