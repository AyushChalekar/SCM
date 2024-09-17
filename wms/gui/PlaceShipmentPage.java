package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PlaceShipmentPage extends JFrame {
    private JTextField shipmentIdField;
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField supplierField;
    private JButton placeShipmentButton;
    private Connection connection;

    public PlaceShipmentPage(Connection connection) {
        this.connection = connection;
        setTitle("Place Shipment");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        shipmentIdField = new JTextField();
        formPanel.add(shipmentIdField);

        formPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        formPanel.add(productNameField);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Supplier:"));
        supplierField = new JTextField();
        formPanel.add(supplierField);

        placeShipmentButton = new JButton("Place Shipment");
        formPanel.add(placeShipmentButton);

        add(formPanel, BorderLayout.CENTER);

        placeShipmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeShipment();
            }
        });
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
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error placing shipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conn = null; // Adjust according to your setup
            try {
                conn = DatabaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new PlaceShipmentPage(conn).setVisible(true);
        });
    }
}
