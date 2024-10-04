package com.wms.ADMIN;

import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ProductPage extends JFrame {
    private Connection connection;
    private UserData userData; // User details

    public ProductPage(Connection connection, UserData userData) {
        this.connection = connection;
        this.userData = userData; // Initialize userData
        setTitle("User Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Set background to white
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        JLabel titleLabel = new JLabel("Product Page", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Title font size
        titleLabel.setForeground(Color.BLACK); // Title text color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1; // Give weight to title
        getContentPane().add(titleLabel, gbc);

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.WHITE); // Set background to white
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0; // Reset weight for navigation panel

        JButton btnCreateOrder = createStyledButton("Inventory", "icons/create_order.png");
        JButton btnTrackOrder = createStyledButton("Categories", "icons/track_order.png");

        btnTrackOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the current content and show CategoriesPage
                getContentPane().removeAll(); // Clear existing components
                CategoriesPage categoriesPage = new CategoriesPage(connection,userData);
                getContentPane().add(categoriesPage);

                // Refresh the frame
                revalidate();
                repaint();
            }
        });

        btnCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductManagementPage().setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        navigationPanel.add(btnCreateOrder, gbc);

        gbc.gridy = 2; // Adjust index for buttons
        navigationPanel.add(btnTrackOrder, gbc);

        gbc.gridy = 3; // Adjust index for navigation panel
        add(navigationPanel, gbc);

        // Back button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        JButton btnBack = createStyledButton("Back to Admin Homepage", "icons/back_to_admin.png");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Product Page
                new AdminHomepage(connection, userData).setVisible(true); // Show the Admin Homepage
            }
        });
        add(btnBack, gbc);

        // Add margins to the buttons
        JButton[] buttons = {btnCreateOrder, btnTrackOrder, btnBack};
        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(250, 60)); // Set button size
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Font size for buttons
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 110, 110)); // Orange background
        button.setForeground(Color.white); // Black text
        button.setFont(new Font("Arial", Font.BOLD, 24)); // Large font size
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Border
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        return button;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
// Initialize UserData as needed
        Connection finalConnection = connection;
        SwingUtilities.invokeLater(() -> new ProductPage(finalConnection, userData).setVisible(true));
    }
}
