package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class SalesPage extends JFrame {
    private Connection connection;

    public SalesPage(Connection connection) {
        this.connection = connection;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sales Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {
            new AdminHomepage(connection).setVisible(true);
            dispose();
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(backButton, BorderLayout.NORTH);

        JLabel salesLabel = new JLabel("Sales Management", SwingConstants.CENTER);
        salesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        salesLabel.setForeground(Color.WHITE);
        mainPanel.add(salesLabel, BorderLayout.CENTER);

        // Add other buttons and panels as necessary

        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
}
