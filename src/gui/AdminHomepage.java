package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminHomepage extends JFrame {

    private Connection connection;

    public AdminHomepage(Connection connection) {
        this.connection = connection;
        setTitle("Admin Homepage");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridLayout(2, 2));
        JButton btnOrderPage = new JButton("Order Page");
        JButton btnShipmentPage = new JButton("Shipment Page");
        JButton btnProductPage = new JButton("Product Page");
        JButton btnUserManagement = new JButton("User Management");

        btnOrderPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderPage(connection).setVisible(true);
            }
        });

        btnShipmentPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShipmentPage(connection).setVisible(true);
            }
        });

        btnProductPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductManagementPage().setVisible(true);
            }
        });

        btnUserManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserManagementPage().setVisible(true);
            }
        });

        navigationPanel.add(btnOrderPage);
        navigationPanel.add(btnShipmentPage);
        navigationPanel.add(btnProductPage);
        navigationPanel.add(btnUserManagement);

        add(navigationPanel, BorderLayout.CENTER);

        // Logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Admin Homepage
                try {
                    new LoginFrame().setVisible(true); // Show the login page
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(btnLogout, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection finalConnection = connection;
        SwingUtilities.invokeLater(() -> new AdminHomepage(finalConnection).setVisible(true));
    }
}
