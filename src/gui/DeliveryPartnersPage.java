package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.UsersPage;
public class DeliveryPartnersPage extends JFrame {
    public DeliveryPartnersPage() {
        setTitle("Delivery Partners Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {
            //new UsersPage().setVisible(true);
            try {
                Connection conn = DatabaseConnection.getConnection();
                new UsersPage(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(backButton, BorderLayout.NORTH);

        JLabel deliveryPartnersLabel = new JLabel("Delivery Partners Management", SwingConstants.CENTER);
        deliveryPartnersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(deliveryPartnersLabel, BorderLayout.CENTER);

        // Display table or list of delivery partners
        String[] columnNames = {"Partner ID", "Name", "Contact"};
        Object[][] data = {
                {"1", "Partner A", "123-456-7890"},
                {"2", "Partner B", "987-654-3210"}
        };
        JTable deliveryPartnersTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(deliveryPartnersTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeliveryPartnersPage().setVisible(true));
    }
}
