package com.wms.services;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class UserLogService {

    private Connection connection;

    public UserLogService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel searchUserLogs(String category, String searchText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Log ID", "User ID", "Action", "Timestamp"}, 0);
        JTable table = new JTable(model);

        String query = "SELECT * FROM user_logs WHERE (category = ? OR ? = 'All Categories') AND action LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, category);
            stmt.setString(3, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("log_id"),
                        rs.getInt("user_id"),
                        rs.getString("action"),
                        rs.getTimestamp("timestamp")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
