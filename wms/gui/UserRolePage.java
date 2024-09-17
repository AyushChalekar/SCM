package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class UserRolePage extends JPanel {
    private String role;

    public UserRolePage(String role) {
        this.role = role;
        setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Dummy table for user roles
        String[] columns = { "User ID", "Username", "Role", "Actions" };
        Object[][] data = {
                // Sample data
                { "1", "john_doe", role, "Edit | Delete" },
                { "2", "jane_smith", role, "Edit | Delete" }
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(new JLabel("Manage " + role, SwingConstants.CENTER), BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back");
        add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "UsersPage"));
    }
}
