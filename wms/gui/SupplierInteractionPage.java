package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SupplierInteractionPage extends JFrame {
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, backButton, logoutButton;

    public SupplierInteractionPage() {
        setTitle("Supplier Interaction");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); // Warm black background

        // Table setup
        String[] columnNames = {"Username", "Email", "IP Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        supplierTable = new JTable(tableModel);
        supplierTable.setFont(new Font("Arial", Font.PLAIN, 20)); // Large fonts
        supplierTable.setForeground(Color.WHITE);
        supplierTable.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        // Back button
        backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the previous page or perform any action
                dispose(); // Close the current frame (example action)
            }
        });
        buttonPanel.add(backButton, BorderLayout.WEST);

        // Logout button
        logoutButton = createStyledButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout
                dispose(); // Close the current frame (example action)
            }
        });
        buttonPanel.add(logoutButton, BorderLayout.EAST);

        // Refresh button
        refreshButton = createStyledButton("Refresh Suppliers");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSuppliers();
            }
        });
        buttonPanel.add(refreshButton, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.NORTH);

        loadSuppliers();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simple animation on hover
                button.setBackground(Color.LIGHT_GRAY);
            }
        });
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.GRAY);
            }
        });
        return button;
    }

    private void loadSuppliers() {
        String query = "SELECT username, email, ip_address FROM users WHERE role_id = (SELECT role_id FROM roles WHERE role_name = 'Supplier')";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Clear existing data in the table
            tableModel.setRowCount(0);

            // Process resultSet to populate supplier data
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String ipAddress = resultSet.getString("ip_address");
                tableModel.addRow(new Object[]{username, email, ipAddress});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupplierInteractionPage().setVisible(true);
            }
        });
    }
}
