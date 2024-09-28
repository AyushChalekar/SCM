package com.wms.gui;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.ShipmentPage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ViewShipmentsPage extends JFrame {
    public ViewShipmentsPage() {
        setTitle("View Shipments Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Title Label
        JLabel titleLabel = new JLabel("View Shipments", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Large font for title
        titleLabel.setForeground(new Color(255, 110, 110)); // Set title color to orange
        add(titleLabel, BorderLayout.NORTH); // Add title to the top

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(new Color(255, 110, 110)); // Set back button color to orange
        backButton.setForeground(Color.WHITE); // Set text color to white
        backButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                new ShipmentPage(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose(); // Close current window
        });

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(backButton, BorderLayout.NORTH);

        // Display table of shipments
        String[] columnNames = {"Shipment ID", "Status", "Date"};
        Object[][] data = {
                {"1", "In Transit", "2024-09-01"},
                {"2", "Delivered", "2024-09-05"}
        };
        JTable shipmentsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(shipmentsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER); // Add the main panel
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewShipmentsPage().setVisible(true));
    }
}
