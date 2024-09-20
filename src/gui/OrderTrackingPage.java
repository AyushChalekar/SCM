package com.wms.gui;

import com.wms.gui.OrderPage;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTrackingPage extends JFrame {

    private Connection connection;
    private JTextArea orderDetailsArea;

    public OrderTrackingPage(Connection connection) {
        this.connection = connection;
        setTitle("Order Tracking Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Order Details Text Area
        orderDetailsArea = new JTextArea();
        orderDetailsArea.setEditable(false);
        orderDetailsArea.setBackground(Color.DARK_GRAY);
        orderDetailsArea.setForeground(Color.WHITE);
        orderDetailsArea.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane orderScrollPane = new JScrollPane(orderDetailsArea);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(20, 20, 20, 20);
        getContentPane().add(orderScrollPane, gbc);

        // Tracking Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.BLACK);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblOrderId = new JLabel("TRACKING ID:");
        lblOrderId.setForeground(Color.WHITE);
        lblOrderId.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField txtOrderId = new JTextField(15);
        txtOrderId.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton btnTrackOrder = createStyledButton("Track Order", "icons/track_order.png");
        btnTrackOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderId = txtOrderId.getText();
                if (!orderId.isEmpty()) {
                    trackOrder(orderId); // Logic to track order based on entered order ID
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Order ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(lblOrderId, formGbc);

        formGbc.gridx = 1;
        formPanel.add(txtOrderId, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = 1;
        formPanel.add(btnTrackOrder, formGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.2;
        getContentPane().add(formPanel, gbc);

        // Back Button
        JButton btnBack = createStyledButton("Back to Order Page", "icons/back.png");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Order Tracking Page
                new OrderPage(connection).setVisible(true); // Show the Order Page
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.0;
        getContentPane().add(btnBack, gbc);

        // Logout Button
        JButton btnLogout = createStyledButton("Logout", "icons/logout.png");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Order Tracking Page
                try {
                    new LoginFrame().setVisible(true); // Show the Login Page
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        gbc.gridx = 1;
        getContentPane().add(btnLogout, gbc);

        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    private void trackOrder(String orderId) {
        // SQL query to track an order by order ID
        String sql = "SELECT * FROM orders WHERE tracking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, orderId); // Set the entered order ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Display order details in the text area
                String orderDetails = "Order ID: " + rs.getString("order_id") + "\n" +
                        "User ID: " + rs.getString("user_id") + "\n" +
                        "Product Details: " + rs.getString("product_details") + "\n" +
                        "Tracking ID: " + rs.getString("tracking_id") + "\n" +
                        "Status: " + rs.getString("status") + "\n";
                orderDetailsArea.setText(orderDetails);
            } else {
                orderDetailsArea.setText("Order not found with ID: " + orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            orderDetailsArea.setText("Error fetching order details.");
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setIcon(new ImageIcon(iconPath));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
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
        SwingUtilities.invokeLater(() -> new OrderTrackingPage(finalConnection).setVisible(true));
    }
}
