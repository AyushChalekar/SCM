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

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {
            //new ShipmentPage().setVisible(true);
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

        JLabel viewShipmentsLabel = new JLabel("View Shipments", SwingConstants.CENTER);
        viewShipmentsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(viewShipmentsLabel, BorderLayout.CENTER);

        // Display table or list of shipments
        String[] columnNames = {"Shipment ID", "Status", "Date"};
        Object[][] data = {
                {"1", "In Transit", "2024-09-01"},
                {"2", "Delivered", "2024-09-05"}
        };
        JTable shipmentsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(shipmentsTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewShipmentsPage().setVisible(true));
    }
}
