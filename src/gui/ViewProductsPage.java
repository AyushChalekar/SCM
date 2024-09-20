package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ViewProductsPage extends JFrame {
    private Connection connection;

    public ViewProductsPage(Connection connection) {
        this.connection = connection;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("View Products");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        JLabel infoLabel = new JLabel("Display all products here", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        infoLabel.setForeground(Color.WHITE);

        add(infoLabel, BorderLayout.CENTER);
    }
}
