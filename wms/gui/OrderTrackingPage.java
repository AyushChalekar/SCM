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

public class OrderTrackingPage extends JFrame {

    private Connection connection;
    private JTextArea orderDetailsArea;

    public OrderTrackingPage(Connection connection) {
        this.connection = connection;
        setTitle("Order Tracking Page");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create the text area to display order details
        this.orderDetailsArea = new JTextArea();
        add(new JScrollPane(orderDetailsArea), BorderLayout.CENTER);

        // Form for tracking orders
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Order ID:"));
        JTextField txtOrderId = new JTextField();
        formPanel.add(txtOrderId);

        // Track Order Button
        JButton btnTrackOrder = new JButton("Track Order");
        btnTrackOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderId = txtOrderId.getText();
                if (!orderId.isEmpty()) {
                    trackOrder(orderId); // Logic to track order based on entered order ID
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Order ID");
                }
            }
        });

        // Add form and button to the layout
        add(formPanel, BorderLayout.NORTH);
        add(btnTrackOrder, BorderLayout.SOUTH);

        // Back button
        JButton btnBack = new JButton("Back to Order Page");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Order Tracking Page
                new OrderPage(connection).setVisible(true); // Show the Order Page
            }
        });
        add(btnBack, BorderLayout.WEST);
    }

    private void trackOrder(String orderId) {
        // SQL query to track an order by order ID
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, orderId); // Set the entered order ID
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Display order details in the text area
                String orderDetails = "Order ID: " + rs.getString("order_id") + "\n" +
                        "User ID: " + rs.getString("user_id") + "\n" +
                        "Product Details: " + rs.getString("product_details") + "\n" +
                        "Order Status: " + rs.getString("order_status") + "\n" +
                        "Tracking ID: " + rs.getString("tracking_id") + "\n" +
                        "Status: " + rs.getString("status") + "\n" +
                        "Order Details: " + rs.getString("order_details");
                orderDetailsArea.setText(orderDetails);
            } else {
                orderDetailsArea.setText("Order not found with ID: " + orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            orderDetailsArea.setText("Error fetching order details.");
        }
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
