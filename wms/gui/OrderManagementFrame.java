package com.wms.gui;

import com.wms.services.OrderService;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;


public class OrderManagementFrame extends JFrame {

        public OrderManagementFrame(String username) {
            setTitle("Order Management");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);

            JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
            mainPanel.setBackground(new Color(40, 40, 40));

            // Create Order
            JButton createOrderButton = new JButton("Create Order");
            createOrderButton.setBackground(new Color(70, 70, 70));
            createOrderButton.setForeground(Color.WHITE);
            mainPanel.add(createOrderButton);

            // Track Order
            JButton trackOrderButton = new JButton("Track Order");
            trackOrderButton.setBackground(new Color(70, 70, 70));
            trackOrderButton.setForeground(Color.WHITE);
            mainPanel.add(trackOrderButton);

            // Delivered Orders
            JButton deliveredOrderButton = new JButton("Delivered Orders");
            deliveredOrderButton.setBackground(new Color(70, 70, 70));
            deliveredOrderButton.setForeground(Color.WHITE);
            mainPanel.add(deliveredOrderButton);

            // Canceled Orders
            JButton canceledOrderButton = new JButton("Canceled Orders");
            canceledOrderButton.setBackground(new Color(70, 70, 70));
            canceledOrderButton.setForeground(Color.WHITE);
            mainPanel.add(canceledOrderButton);

            // Logout and Back buttons
            JButton logoutButton = new JButton("Logout");
            JButton backButton = new JButton("Back");
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(backButton, BorderLayout.WEST);
            bottomPanel.add(logoutButton, BorderLayout.EAST);
            mainPanel.add(bottomPanel);

            add(mainPanel);

            createOrderButton.addActionListener(e -> new CreateOrderFrame(username));
            trackOrderButton.addActionListener(e -> new TrackOrderFrame(username));
            deliveredOrderButton.addActionListener(e -> new DeliveredOrdersFrame(username));
            canceledOrderButton.addActionListener(e -> new CanceledOrdersFrame(username));
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
                try {
                    new LoginFrame();  // Go back to the previous screen
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
    }

