package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PlaceShipmentPage extends JFrame {
    private JTextField shipmentIdField;
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField supplierField;
    private JButton placeShipmentButton;
    private JButton backButton;
    private JButton logoutButton;
    private Connection connection;
    private JTable shipmentTable;
    private DefaultTableModel tableModel;

    public PlaceShipmentPage(Connection connection) {
        this.connection = connection;
        setTitle("Place Shipment");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#2E2E2E")); // Warm black background
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.decode("#ffffff"));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels and Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Shipment ID:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        shipmentIdField = new JTextField(20);
        formPanel.add(shipmentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Product Name:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        productNameField = new JTextField(20);
        formPanel.add(productNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(20);
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Supplier:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        supplierField = new JTextField(20);
        formPanel.add(supplierField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        placeShipmentButton = createStyledButton("Place Shipment", "icons/track.png");
        formPanel.add(placeShipmentButton, gbc);

        add(formPanel, BorderLayout.WEST);

        // Table for displaying shipments
        tableModel = new DefaultTableModel(new String[]{"Shipment ID", "Product Name", "Quantity", "Supplier", "Status", "Tracking ID"}, 0);
        shipmentTable = new JTable(tableModel);
        shipmentTable.setFillsViewportHeight(true);
        shipmentTable.setBackground(Color.decode("#3C3C3C")); // Dark gray background for table
        shipmentTable.setForeground(Color.WHITE);
        shipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane tableScrollPane = new JScrollPane(shipmentTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Back and Logout Buttons
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.decode("#2E2E2E"));
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        GridBagConstraints gbcBottom = new GridBagConstraints();
        gbcBottom.insets = new Insets(10, 10, 10, 10);
        gbcBottom.anchor = GridBagConstraints.SOUTH;

        gbcBottom.gridx = 0;
        gbcBottom.gridy = 0;
        backButton = createStyledButton("Back", "icons/back.png");
        bottomPanel.add(backButton, gbcBottom);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Order Page
                new ShipmentPage(connection).setVisible(true); // Show the Admin Homepage
            }
        });
        gbcBottom.gridx = 1;
        logoutButton = createStyledButton("Logout", "icons/logout.png");
        bottomPanel.add(logoutButton, gbcBottom);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load existing shipments
        loadShipments();

        // Place Shipment Button Action
        placeShipmentButton.addActionListener(e -> placeShipment());
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

    private void placeShipment() {
        try {
            String shipmentId = shipmentIdField.getText();
            String productName = productNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            String supplier = supplierField.getText();

            String sql = "INSERT INTO shipments (shipment_id, product_name, quantity, supplier) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, shipmentId);
                stmt.setString(2, productName);
                stmt.setInt(3, quantity);
                stmt.setString(4, supplier);
                stmt.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Shipment placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reload shipments after placing a new one
            loadShipments();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error placing shipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadShipments() {
        // Clear the current table data
        tableModel.setRowCount(0);

        String sql = "SELECT * FROM shipments";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("shipment_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getString("supplier"),
                        rs.getString("shipment_status"),
                        rs.getString("tracking_id")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading shipments: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conn = null;
            try {
                conn = DatabaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new PlaceShipmentPage(conn).setVisible(true);
        });
    }
}
