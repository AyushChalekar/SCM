package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UsersPage extends JFrame {
    private Connection connection;

    public UsersPage(Connection connection) {
        this.connection = connection;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Users Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton backButton = createButton("Back", e -> showPage(new AdminHomepage(connection)));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(backButton, gbc);

        JLabel usersLabel = new JLabel("Users Management", SwingConstants.CENTER);
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        usersLabel.setForeground(Color.WHITE);

        gbc.gridy = 1;
        add(usersLabel, gbc);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.addActionListener(actionListener);
        return button;
    }

    private void showPage(JFrame page) {
        page.setVisible(true);
        dispose();
    }
}
