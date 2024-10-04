package com.wms.CUSTOMER;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryUpdatePage extends JFrame {
    private JTextField trackingIdField;
    private JTextArea deliveryUpdatesArea, commentsArea;
    private JButton updateStatusButton, backButton, logoutButton;
    private JComboBox<String> statusComboBox;
    private Connection connection;

    public DeliveryUpdatePage(Connection connection) {
        this.connection = connection;
        setTitle("Delivery Update");
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

        JLabel trackingIdLabel = new JLabel("Tracking ID:");
        trackingIdLabel.setForeground(new Color(255, 110, 110)); // Orange color
        trackingIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(trackingIdLabel, gbc);

        trackingIdField = new JTextField();
        trackingIdField.setFont(new Font("Arial", Font.PLAIN, 18));
        trackingIdField.setBackground(Color.WHITE);
        trackingIdField.setForeground(Color.BLACK); // Black text
        gbc.gridx = 1;
        formPanel.add(trackingIdField, gbc);

        JLabel statusLabel = new JLabel("Update Status:");
        statusLabel.setForeground(new Color(255, 110, 110)); // Orange color
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(statusLabel, gbc);

        String[] statuses = {"In Progress", "Shipped", "Out for Delivery", "Delivered", "Canceled"};
        statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        formPanel.add(statusComboBox, gbc);

        // Comments area setup (above the Update Status button)
        JLabel commentsLabel = new JLabel("Comments/Notes:");
        commentsLabel.setForeground(new Color(255, 110, 110)); // Orange color
        commentsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span both columns
        formPanel.add(commentsLabel, gbc);

        commentsArea = new JTextArea(5, 20);
        commentsArea.setFont(new Font("Arial", Font.PLAIN, 18));
        commentsArea.setBackground(Color.WHITE);
        commentsArea.setForeground(Color.BLACK); // Black text
        JScrollPane commentsScrollPane = new JScrollPane(commentsArea);
        gbc.gridy = 3;
        formPanel.add(commentsScrollPane, gbc);

        // Update Status button
        updateStatusButton = createStyledButton("Update Status", "icons/update.png");
        gbc.gridy = 4;
        formPanel.add(updateStatusButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Delivery updates area setup
        deliveryUpdatesArea = new JTextArea();
        deliveryUpdatesArea.setEditable(false);
        deliveryUpdatesArea.setFont(new Font("Arial", Font.PLAIN, 18));
        deliveryUpdatesArea.setBackground(Color.WHITE);
        deliveryUpdatesArea.setForeground(Color.BLACK); // Black text
        JScrollPane updatesScrollPane = new JScrollPane(deliveryUpdatesArea);
        add(updatesScrollPane, BorderLayout.CENTER);

        // Buttons setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        buttonPanel.setBackground(Color.WHITE);

        backButton = createStyledButton("Back", "icons/back.png");
        backButton.addActionListener(e -> dispose()); // Close the current frame
        buttonPanel.add(backButton, BorderLayout.WEST);

        logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(e -> dispose()); // Close the current frame
        buttonPanel.add(logoutButton, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        // Update status button action
        updateStatusButton.addActionListener(e -> updateStatus());
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Orange button color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));
        button.setPreferredSize(new Dimension(200, 50)); // Button size

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 110, 110), 2),
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
                button.setBackground(new Color(255, 180, 180)); // Lighter orange on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Revert back to original orange
            }
        });

        return button;
    }

    private void updateStatus() {
        try {
            String tracking_id = trackingIdField.getText();
            String status = (String) statusComboBox.getSelectedItem();
            String order_deatils = commentsArea.getText(); // Change to orderDetails

            // Update the order status in the database
            String sql = "UPDATE orders SET status = ?, order_deatils = ? WHERE tracking_id = ?"; // Correct the column name
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setString(2, order_deatils);
                stmt.setString(3, tracking_id);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    deliveryUpdatesArea.append("Updated status for Tracking ID: " + tracking_id + " to " + status + "\n");
                } else {
                    JOptionPane.showMessageDialog(this, "No order found with this Tracking ID: " + tracking_id, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating order: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            new DeliveryUpdatePage(conn).setVisible(true);
        });
    }
}
