package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.BLACK);
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonLabels = {"Order Page", "Shipment Page", "Product Page", "User Management"};
        ActionListener[] buttonListeners = {
                e -> new OrderPage(connection).setVisible(true),
                e -> new ShipmentPage(connection).setVisible(true),
                e -> new ProductManagementPage().setVisible(true),
                e -> new UserManagementPage().setVisible(true)
        };

        JButton[] buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createStyledButton(buttonLabels[i]);
            buttons[i].addActionListener(buttonListeners[i]);
            GridBagConstraints buttonGbc = new GridBagConstraints();
            buttonGbc.gridx = 0;
            buttonGbc.gridy = i;
            buttonGbc.insets = new Insets(10, 10, 10, 10);
            navigationPanel.add(buttons[i], buttonGbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        getContentPane().add(navigationPanel, gbc);

        // Logout button at the bottom right
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnLogout = createStyledButton("Logout");
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
        buttonPanel.add(btnLogout, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.0;
        getContentPane().add(buttonPanel, gbc);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 40)); // Use a blue color for buttons
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setPreferredSize(new Dimension(200, 50)); // Make buttons a bit larger
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
        SwingUtilities.invokeLater(() -> new AdminHomepage(finalConnection).setVisible(true));
    }
}
