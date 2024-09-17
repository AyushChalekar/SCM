package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ShipmentTrackingPage extends JFrame {
    private JTextField shipmentIdField;
    private JTextArea shipmentDetailsArea;
    private JButton trackShipmentButton;
    private Connection connection;

    public ShipmentTrackingPage(Connection connection) {
        this.connection = connection;
        setTitle("Track Shipment");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        shipmentIdField = new JTextField();
        formPanel.add(shipmentIdField);

        trackShipmentButton = new JButton("Track Shipment");
        formPanel.add(trackShipmentButton);

        add(formPanel, BorderLayout.NORTH);

        shipmentDetailsArea = new JTextArea();
        shipmentDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(shipmentDetailsArea);
        add(scrollPane, BorderLayout.CENTER);

        trackShipmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trackShipment();
            }
        });
    }

    private void trackShipment() {
        try {
            String shipmentId = shipmentIdField.getText();
            String sql = "SELECT * FROM shipments WHERE shipment_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, shipmentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    shipmentDetailsArea.setText("");
                    if (rs.next()) {
                        shipmentDetailsArea.append("Shipment ID: " + rs.getString("shipment_id") + "\n");
                        shipmentDetailsArea.append("Product Name: " + rs.getString("product_name") + "\n");
                        shipmentDetailsArea.append("Quantity: " + rs.getInt("quantity") + "\n");
                        shipmentDetailsArea.append("Supplier: " + rs.getString("supplier") + "\n");
                    } else {
                        shipmentDetailsArea.setText("No shipment found with ID: " + shipmentId);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error tracking shipment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            new ShipmentTrackingPage(conn).setVisible(true);
        });
    }
}

