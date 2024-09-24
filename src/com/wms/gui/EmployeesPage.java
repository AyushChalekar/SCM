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

        JLabel employeesLabel = new JLabel("Employees Management", SwingConstants.CENTER);
        employeesLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
