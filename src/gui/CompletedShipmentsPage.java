package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.ShipmentPage;



public class CompletedShipmentsPage extends JFrame {
    public CompletedShipmentsPage() {
        setTitle("Completed Shipments Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {
           // new ShipmentPage().setVisible(true);
            try {
                Connection conn = DatabaseConnection.getConnection();
                new ShipmentPage(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(backButton, BorderLayout.NORTH);

        JLabel completedShipmentsLabel = new JLabel("Completed Shipments", SwingConstants.CENTER);
        completedShipmentsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(completedShipmentsLabel, BorderLayout.CENTER);

        // Display table or list of completed shipments
        String[] columnNames = {"Shipment ID", "Delivered Date"};
        Object[][] data = {
                {"1", "2024-09-05"},
                {"2", "2024-09-06"}
        };
        JTable completedShipmentsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(completedShipmentsTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompletedShipmentsPage().setVisible(true));
    }
}
