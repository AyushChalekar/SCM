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
        getContentPane().setBackground(Color.WHITE); // White background

        JLabel infoLabel = new JLabel("Update product details here", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        infoLabel.setForeground(Color.BLACK); // Black text

        add(infoLabel, BorderLayout.CENTER);

        // Example buttons with orange color
        JButton saveButton = createStyledButton("Save");
        JButton cancelButton = createStyledButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); // White background for button panel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
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
        // Example connection object; replace with your actual connection logic
        Connection connection = null; // Your connection logic here
        SwingUtilities.invokeLater(() -> new UpdateProductPage(connection).setVisible(true));
    }
}
