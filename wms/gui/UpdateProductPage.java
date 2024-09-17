package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UpdateProductPage extends JFrame {
    private Connection connection;

    public UpdateProductPage(Connection connection) {
        this.connection = connection;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Update Product");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        JLabel infoLabel = new JLabel("Update product details here", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        infoLabel.setForeground(Color.WHITE);

        add(infoLabel, BorderLayout.CENTER);
    }
}
