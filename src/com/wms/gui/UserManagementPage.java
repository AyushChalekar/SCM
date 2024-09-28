package com.wms.gui;

import java.awt.*;
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
        setBackground(Color.WHITE); // White background

        // Create a "Load Users" button
        JButton loadUsersButton = createStyledButton("Load Users");
        loadUsersButton.setBounds(20, 20, 150, 30);
        add(loadUsersButton);

        // Table to display users
        userTableModel = new DefaultTableModel(new Object[]{"User ID", "First Name", "Last Name"}, 0);
        userTable = new JTable(userTableModel);
        userTable.setBounds(20, 60, 600, 400);
        userTable.setBackground(Color.LIGHT_GRAY); // Optional: Light gray for table
        userTable.setForeground(Color.BLACK); // Black text for table
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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large font for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Orange background
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40)); // Set size for buttons

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(new UserManagementPage());
            frame.setVisible(true);
        });
    }
}
