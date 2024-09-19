package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CanceledOrdersFrame extends JFrame {

    public CanceledOrdersFrame(String username) {
        setTitle("Canceled Orders");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(40, 40, 40));

        JTextArea ordersArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(ordersArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add logic to display canceled orders
        displayCanceledOrders(username);

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

    private void displayCanceledOrders(String username) {
        // Add database interaction code here to display canceled orders
        // Example SQL command: SELECT * FROM canceled_orders WHERE user_id = ?;
    }
}
