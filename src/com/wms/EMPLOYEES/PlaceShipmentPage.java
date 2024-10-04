package com.wms.EMPLOYEES;

import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import com.wms.ADMIN.LoginFrame;

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
    private UserData userData; // Assuming UserData class is implemented

    public PlaceShipmentPage(Connection connection, UserData userData) {
        this.connection = connection;
        this.userData = userData; // Store user data

        setTitle("Place Shipment");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Set background to white
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Set form panel background to white
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

        add(formPanel, BorderLayout.EAST);

        // Table for displaying shipments
        tableModel = new DefaultTableModel(new String[]{"Shipment ID", "Product Name", "Quantity", "Supplier", "Status", "Tracking ID"}, 0);
        shipmentTable = new JTable(tableModel);
        shipmentTable.setFillsViewportHeight(true);
        shipmentTable.setBackground(Color.WHITE); // Set table background to white
        shipmentTable.setForeground(Color.BLACK); // Set table text color to black
        shipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane tableScrollPane = new JScrollPane(shipmentTable);
        add(tableScrollPane, BorderLayout.CENTER);
        // Back and Logout Buttons
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE); // Set bottom panel background to white
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
                new ShipmentPage(connection,userData).setVisible(true); // Show the Admin Homepage
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
        button.setForeground(Color.white); // Set button text color to black
        button.setBackground(new Color(255, 110, 110)); // Set button background color to orange
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        // Adjust button size to ensure text fits

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        )); // Rounded corners with shadow
        button.setPreferredSize(new Dimension(200, 50));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        // Button animations
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#FF8C8C")); // Light orange
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Orange
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.decode("#FF4B4B")); // Dark orange
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Orange
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
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
            new PlaceShipmentPage(conn,userData).setVisible(true);
        });
    }
}
