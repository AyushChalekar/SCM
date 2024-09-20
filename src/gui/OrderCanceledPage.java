package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OrderCanceledPage extends JFrame {
    private JTable deliveredOrdersTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public OrderCanceledPage(Connection connection) {
        this.connection = connection;

        // Set window properties
        setTitle("Delivered Orders");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen mode
        setUndecorated(true); // Remove window borders and title bar
        getContentPane().setBackground(Color.decode("#282828")); // Warm black background
        setLayout(new BorderLayout());

        // Create table model and JTable to display delivered orders
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Product ID", "Order Status", "Tracking ID"}, 0);
        deliveredOrdersTable = new JTable(tableModel);
        deliveredOrdersTable.setFillsViewportHeight(true);
        deliveredOrdersTable.setFont(new Font("Arial", Font.PLAIN, 16)); // Large font for readability
        deliveredOrdersTable.setRowHeight(30); // Increase row height for visibility
        deliveredOrdersTable.setBackground(Color.decode("#3C3C3C")); // Dark gray table background
        deliveredOrdersTable.setForeground(Color.WHITE); // White text for readability
        deliveredOrdersTable.setGridColor(Color.decode("#4B4B4B")); // Table grid color

        JScrollPane tableScrollPane = new JScrollPane(deliveredOrdersTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load delivered orders into the table
        refreshDeliveredOrders();

        // Create bottom panel for back and logout buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode("#282828"));
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Back Button (Top-left corner)
        JButton backButton = createStyledButton("Back", "icons/back.png");
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Logout Button (Bottom-right corner)
        JButton logoutButton = createStyledButton("Logout", "icons/logout.png");
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        backButton.addActionListener(e -> goBack());
        logoutButton.addActionListener(e -> logout());

        // Allow table editing and saving
        deliveredOrdersTable.getModel().addTableModelListener(e -> saveTableChanges());

        // Set close operation and visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void refreshDeliveredOrders() {
        String query = "SELECT order_id,product_id, product_details,tracking_id " +
                "FROM wms1.orders  " +
                "WHERE status = 'Delivered'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Create a table model to hold the results
            DefaultTableModel tableModel = new DefaultTableModel(
                    new String[] {"Order ID", "Product ID", "Product Details", "Tracking ID"}, 0);

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int productId = resultSet.getInt("product_id");
                String productDetails = resultSet.getString("product_details");
                String trackingId = resultSet.getString("tracking_id");

                // Add row to table model
                tableModel.addRow(new Object[] {orderId, productId, productDetails, trackingId});
            }

            // Use table model to populate JTable
            JTable ordersTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(ordersTable);
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            revalidate(); // Refresh the UI

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void saveTableChanges() {
        try {
            for (int i = 0; i < deliveredOrdersTable.getRowCount(); i++) {
                String updateSql = "UPDATE orders SET product_name = ?, order_status = ? WHERE order_id = ?";
                PreparedStatement stmt = connection.prepareStatement(updateSql);
                stmt.setString(1, (String) tableModel.getValueAt(i, 1)); // product_name
                stmt.setString(2, (String) tableModel.getValueAt(i, 2)); // order_status
                stmt.setInt(3, (Integer) tableModel.getValueAt(i, 0)); // order_id
                stmt.executeUpdate();
                stmt.close();
            }
            JOptionPane.showMessageDialog(this, "Changes saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving changes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        // Adjust button size to ensure text fits
        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        )); // Rounded corners with shadow


        // Button animations
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#8C8C8C")); // Light gray
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.decode("#6D6D6D")); // Gray
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.decode("#4B4B4B")); // Dark gray
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(Color.decode("#6D6D6D")); // Gray
            }
        });

        return button;
    }

    private void goBack() {
        dispose(); // Close the current window
        // Navigate to the previous page (implement your logic here)
    }

    private void logout() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            dispose(); // Close the current window
            try {
                new LoginFrame(); // Navigate to the login page
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conn;
            try {
                conn = DatabaseConnection.getConnection();
                new OrderDeliveredPage(conn).setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
