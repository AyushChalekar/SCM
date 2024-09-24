package com.wms.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.wms.utils.DatabaseConnection;

public class UserManagementPage extends JPanel {
    private JTable userTable;
    private DefaultTableModel userTableModel;

    public UserManagementPage() {
        setLayout(null);

        // Create a "Load Users" button
        JButton loadUsersButton = new JButton("Load Users");
        loadUsersButton.setBounds(20, 20, 150, 30);
        add(loadUsersButton);

        // Table to display users
        userTableModel = new DefaultTableModel(new Object[]{"User ID", "First Name", "Last Name"}, 0);
        userTable = new JTable(userTableModel);
        userTable.setBounds(20, 60, 600, 400);
        add(userTable);

        // Action listener for Load Users button
        loadUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUsers();
            }
        });
    }

    // Method to load users into the table
    public void loadUsers() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "SELECT user_id, first_name, last_name FROM users";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            userTableModel.setRowCount(0);  // Clear previous rows

            // Populate table with data
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                userTableModel.addRow(new Object[]{userId, firstName, lastName});
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            new UserManagementPage().setVisible(true);
        });
    }
}
