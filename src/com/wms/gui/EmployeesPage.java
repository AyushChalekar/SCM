package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.UsersPage;

public class EmployeesPage extends JFrame {
    public EmployeesPage() {
        setTitle("Employees Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set button and titles to orange (255, 110, 110)
        Color orangeColor = new Color(255, 110, 110);

        // Back button customization
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(orangeColor); // Set button background to orange
        backButton.setForeground(Color.BLACK); // Set text color to black
        backButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                new UsersPage(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        // Main panel customization
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Set background to white
        mainPanel.add(backButton, BorderLayout.NORTH);

        // Employees title label customization
        JLabel employeesLabel = new JLabel("Employees Management", SwingConstants.CENTER);
        employeesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        employeesLabel.setForeground(orangeColor); // Set title color to orange
        mainPanel.add(employeesLabel, BorderLayout.CENTER);

        // Display table or list of employees
        String[] columnNames = {"Employee ID", "Name", "Position"};
        Object[][] data = {
                {"1", "Employee A", "Manager"},
                {"2", "Employee B", "Staff"}
        };
        JTable employeesTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(employeesTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeesPage().setVisible(true));
    }
}
