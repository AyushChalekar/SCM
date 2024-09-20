package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.UsersPage;
public class CustomersPage extends JFrame {
    public CustomersPage() {
        setTitle("Customers Page");
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

        JLabel customersLabel = new JLabel("Customers Management", SwingConstants.CENTER);
        customersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(customersLabel, BorderLayout.CENTER);

        // Display table or list of customers
        String[] columnNames = {"Customer ID", "Name", "Email"};
        Object[][] data = {
                {"1", "Customer A", "a@example.com"},
                {"2", "Customer B", "b@example.com"}
        };
        JTable customersTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(customersTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomersPage().setVisible(true));
    }
}
