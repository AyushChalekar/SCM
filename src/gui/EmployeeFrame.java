package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import com.wms.utils.DatabaseConnection;
import com.wms.gui.LoginFrame;

public class EmployeeFrame extends JFrame {
    public EmployeeFrame() {
        setTitle("Employee - Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Employee Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(titleLabel, c);

        JButton logoutButton = new JButton("Logout");
        c.gridx = 1;
        c.gridy = 1;
        panel.add(logoutButton, c);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // new LoginFrame().setVisible(true);
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    new LoginFrame().setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }
        });

        add(panel);
    }
}
