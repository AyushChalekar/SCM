package com.wms.ADMIN;

import com.wms.utils.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ShipmentTrackingPage extends JFrame {
    private JTextField shipmentIdField;
    private JTextArea shipmentDetailsArea;
    private JButton trackShipmentButton, backButton, logoutButton;
    private Connection connection;

    public ShipmentTrackingPage(Connection connection) {
        this.connection = connection;
        setTitle("Track Shipment");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // White background

        // Form panel setup
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel shipmentIdLabel = new JLabel("Shipment ID:");
        shipmentIdLabel.setForeground(Color.BLACK); // Black text
        shipmentIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(shipmentIdLabel, gbc);

        shipmentIdField = new JTextField();
        shipmentIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        shipmentIdField.setForeground(Color.BLACK); // Black text
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(shipmentIdField, gbc);

        trackShipmentButton = createStyledButton("Track Shipment", "icons/track.png");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(trackShipmentButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Shipment details area setup
        shipmentDetailsArea = new JTextArea();
        shipmentDetailsArea.setEditable(false);
        shipmentDetailsArea.setFont(new Font("Arial", Font.PLAIN, 18));
        shipmentDetailsArea.setForeground(Color.BLACK); // Black text
        JScrollPane scrollPane = new JScrollPane(shipmentDetailsArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding
        buttonPanel.setBackground(Color.WHITE); // White background

        backButton = createStyledButton("Back", "icons/back.png");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
            }
        });
        buttonPanel.add(backButton, BorderLayout.WEST);

        logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
            }
        });
        buttonPanel.add(logoutButton, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        // Track shipment button action
        trackShipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trackShipment();
            }
        });
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.white); // Black text
        button.setBackground(new Color(255, 110, 110)); // Orange background color
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


        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        // Button animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Revert to orange background
            }
        });

        return button;
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
                        // Remove or replace this line based on actual columns
                        // shipmentDetailsArea.append("Supplier: " + rs.getString("supplier") + "\n");
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


