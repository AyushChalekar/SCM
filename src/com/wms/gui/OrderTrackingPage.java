package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderTrackingPage extends JFrame {
    private JTextField shipmentIdField;
    private JTextArea shipmentDetailsArea;
    private JButton trackShipmentButton, backButton, logoutButton;
    private Connection connection;

    public OrderTrackingPage(Connection connection) {
        this.connection = connection;
        setTitle("Track Orders");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // White background

        // Form panel setup
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // White background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel shipmentIdLabel = new JLabel("Tracking ID:");
        shipmentIdLabel.setForeground(Color.BLACK); // Black text
        shipmentIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(shipmentIdLabel, gbc);

        shipmentIdField = new JTextField();
        shipmentIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        shipmentIdField.setBackground(Color.LIGHT_GRAY); // Light gray for text field
        shipmentIdField.setForeground(Color.BLACK); // Black text
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(shipmentIdField, gbc);

        trackShipmentButton = createStyledButton("Track Order", "icons/track.png");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(trackShipmentButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Shipment details area setup
        shipmentDetailsArea = new JTextArea();
        shipmentDetailsArea.setEditable(false);
        shipmentDetailsArea.setFont(new Font("Arial", Font.PLAIN, 18));
        shipmentDetailsArea.setBackground(Color.LIGHT_GRAY); // Light gray for text area
        shipmentDetailsArea.setForeground(Color.BLACK); // Black text
        JScrollPane scrollPane = new JScrollPane(shipmentDetailsArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
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
                track();
            }
        });
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE); // White text
        button.setBackground(new Color(255, 110, 110)); // Orange background
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

        // Button animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Orange background
            }
        });

        return button;
    }

    private void track() {
        try {
            String trackingId = shipmentIdField.getText();
            String sql = "SELECT o.tracking_id, u.username, o.product_id, o.quantity, o.status, o.order_deatils " +
                    "FROM orders o JOIN users u ON o.user_id = u.user_id WHERE o.tracking_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, trackingId);
                try (ResultSet rs = stmt.executeQuery()) {
                    shipmentDetailsArea.setText("");
                    if (rs.next()) {
                        shipmentDetailsArea.append("Tracking ID: " + rs.getString("tracking_id") + "\n");
                        shipmentDetailsArea.append("Username: " + rs.getString("username") + "\n");
                        shipmentDetailsArea.append("Product ID: " + rs.getInt("product_id") + "\n");
                        shipmentDetailsArea.append("Quantity: " + rs.getInt("quantity") + "\n");
                        shipmentDetailsArea.append("Status: " + rs.getString("status") + "\n");
                        shipmentDetailsArea.append("Order Details: " + rs.getString("order_deatils") + "\n");
                    } else {
                        shipmentDetailsArea.setText("No Order found with this ID: " + trackingId);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error tracking Order: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            new OrderTrackingPage(conn).setVisible(true);
        });
    }
}
