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
        getContentPane().setBackground(Color.WHITE); // Set background to white

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                new ShipmentPage(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Set main panel background to white
        mainPanel.add(backButton, BorderLayout.NORTH);

        JLabel completedShipmentsLabel = new JLabel("Completed Shipments", SwingConstants.CENTER);
        completedShipmentsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        completedShipmentsLabel.setForeground(Color.BLACK); // Set label text to black
        mainPanel.add(completedShipmentsLabel, BorderLayout.CENTER);

        // Display table or list of completed shipments
        String[] columnNames = {"Shipment ID", "Delivered Date"};
        Object[][] data = {
                {"1", "2024-09-05"},
                {"2", "2024-09-06"}
        };
        JTable completedShipmentsTable = new JTable(data, columnNames);
        completedShipmentsTable.setFont(new Font("Arial", Font.PLAIN, 18)); // Set table font
        completedShipmentsTable.setRowHeight(30); // Adjust row height for larger text
        JScrollPane scrollPane = new JScrollPane(completedShipmentsTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Original orange color for buttons
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 50)); // Increase button size as needed

        // Button animation
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(255, 150, 150)); // Lighter orange on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Reset to original orange
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompletedShipmentsPage().setVisible(true));
    }
}
