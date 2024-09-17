package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderPage extends JFrame {

    private Connection connection;

    public OrderPage(Connection connection) {
        this.connection = connection;
        setTitle("Order Page");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridLayout(4, 1));
        JButton btnCreateOrder = new JButton("Create Order");
        JButton btnTrackOrder = new JButton("Track Order");
        JButton btnOrderDelivered = new JButton("Order Delivered");
        JButton btnOrderCanceled = new JButton("Order Canceled");

        btnCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateOrderPage(connection).setVisible(true);
            }
        });

        btnTrackOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderTrackingPage(connection).setVisible(true);
            }
        });

        btnOrderDelivered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderDeliveredPage(connection).setVisible(true);
            }
        });

        btnOrderCanceled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderCanceledPage().setVisible(true);
            }
        });

        navigationPanel.add(btnCreateOrder);
        navigationPanel.add(btnTrackOrder);
        navigationPanel.add(btnOrderDelivered);
        navigationPanel.add(btnOrderCanceled);

        add(navigationPanel, BorderLayout.CENTER);

        // Back button
        JButton btnBack = new JButton("Back to Admin Homepage");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Order Page
                new AdminHomepage(connection).setVisible(true); // Show the Admin Homepage
            }
        });
        add(btnBack, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection finalConnection = connection;
        SwingUtilities.invokeLater(() -> new OrderPage(finalConnection).setVisible(true));
    }
}
