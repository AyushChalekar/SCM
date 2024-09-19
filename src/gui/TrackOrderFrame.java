package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TrackOrderFrame extends JFrame {

    public TrackOrderFrame(String username) {
        setTitle("Track Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.setBackground(new Color(40, 40, 40));

        JLabel trackingIdLabel = new JLabel("Tracking ID:");
        trackingIdLabel.setForeground(Color.WHITE);
        JTextField trackingIdField = new JTextField();

        JButton trackOrderButton = new JButton("Track Order");
        trackOrderButton.setBackground(new Color(70, 70, 70));
        trackOrderButton.setForeground(Color.WHITE);

        mainPanel.add(trackingIdLabel);
        mainPanel.add(trackingIdField);
        mainPanel.add(trackOrderButton);

        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(bottomPanel);

        add(mainPanel);

        trackOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trackingId = trackingIdField.getText();

                // Add logic to track order in the database
                trackOrder(username, trackingId);
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
            new OrderManagementFrame(username);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void trackOrder(String username, String trackingId) {
        // Add database interaction code here to track an order
        // Example SQL command: SELECT * FROM orders WHERE tracking_id = ? AND user_id = ?;
    }
}
