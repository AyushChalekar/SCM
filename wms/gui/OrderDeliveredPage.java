package com.wms.gui;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderDeliveredPage extends JFrame {
    private JTextArea deliveredOrdersArea;
    private JButton refreshButton;
    private Connection connection;

    public OrderDeliveredPage(Connection connection) {
        this.connection = connection;
        setTitle("Delivered Orders");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        deliveredOrdersArea = new JTextArea();
        deliveredOrdersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(deliveredOrdersArea);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh Delivered Orders");
        add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshDeliveredOrders();
            }
        });

        refreshDeliveredOrders(); // Load delivered orders on startup
    }

    public void refreshDeliveredOrders() {
        try {
            Connection conn = null;
            try {
                conn = DatabaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Adjusted SQL query with a JOIN to get product_name
            String query = "SELECT o.order_id, o.product_details, o.order_status, p.product_name " +
                    "FROM orders o " +
                    "JOIN products p ON o.product_id = p.product_id " +
                    "WHERE o.order_status = 'Delivered'";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                // Process the result set
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    String productName = rs.getString("product_name"); // From products table
                    String orderStatus = rs.getString("order_status");
                    String trackingId = rs.getString("tracking_id");

                    // Example code to refresh the display


                    // Add to your UI or data model
                    System.out.println("Order ID: " + orderId + ", Product: " + productName +
                            ", Status: " + orderStatus + ", Tracking ID: " + trackingId);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {

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
            new OrderDeliveredPage(conn).setVisible(true);
        });
    }
}
