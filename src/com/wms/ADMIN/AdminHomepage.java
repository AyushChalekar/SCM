package com.wms.ADMIN;

import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.ImageIcon;
public class AdminHomepage extends JFrame {

    private Connection connection;
    private UserData userData; // User details

    public AdminHomepage(Connection connection, UserData userData) {
        this.connection = connection;
        this.userData = userData; // Initialize userData

        setTitle("Admin Homepage");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // White background
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE); // Keep the design consistent

// Welcome label with increased font size
        JLabel welcomeLabel = new JLabel("Welcome, " + userData.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 42)); // Increased font size
        welcomeLabel.setForeground(new Color(255, 110, 110)); // Matches your color scheme

        topPanel.add(welcomeLabel, BorderLayout.WEST);

// Create settings icon button
        JButton btnset = new JButton(new ImageIcon(getClass().getResource("/icons/settings.png"))); // Use the icon directly
        btnset.setBorder(BorderFactory.createEmptyBorder());
        btnset.setContentAreaFilled(false); // Make it look like just an icon
        btnset.setToolTipText("Redirect to Settings Page"); // Updated tooltip for clarity

        btnset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SettingsPage(userData).setVisible(true); // Pass user data to SettingsPage
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

// Add the icon button to the right side of the top panel
        topPanel.add(btnset, BorderLayout.EAST);

// Add the top panel to your main layout
        gbc.gridx = 0;
        gbc.gridy = 0; // First row for the top panel
        gbc.weighty = 0.1; // Give it some vertical space
        getContentPane().add(topPanel, gbc);

        // Navigation Panel
        JPanel navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.WHITE); // White background for panel
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonLabels = {"Order Page", "Shipment Page", "Product Page", "User Management"};
        ActionListener[] buttonListeners = {
                e -> new OrderPage(connection, userData).setVisible(true),
                e -> new ShipmentPage(connection, userData).setVisible(true),
                e -> new ProductPage(connection, userData).setVisible(true),
                e -> new UsersPage(connection, userData).setVisible(true)
        };

        JButton[] buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createStyledButton(buttonLabels[i], ""); // Removed circle icon reference
            buttons[i].addActionListener(buttonListeners[i]);
            GridBagConstraints buttonGbc = new GridBagConstraints();
            buttonGbc.gridx = 0;
            buttonGbc.gridy = i;
            buttonGbc.insets = new Insets(10, 10, 10, 10);
            navigationPanel.add(buttons[i], buttonGbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 1; // Next row for the navigation panel
        gbc.weighty = 1.0; // Remaining space
        getContentPane().add(navigationPanel, gbc);

        // Logout button at the bottom right
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE); // White background for button panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnLogout = createStyledButton("Logout", "/icons/logout.png"); // Set logout icon
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the Admin Homepage
                try {
                    new LoginFrame().setVisible(true); // Show the login page
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonPanel.add(btnLogout, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 2; // Next row for the button panel
        gbc.weighty = 0.0; // No additional vertical space
        getContentPane().add(buttonPanel, gbc);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(200, 50));

        if (!iconPath.isEmpty()) { // Only set the icon if an icon path is provided
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                button.setIcon(icon);
            } catch (Exception e) {
                System.err.println("Icon not found: " + iconPath);
                e.printStackTrace();
            }
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
        Connection finalConnection = connection;

        // Assuming you have already created a UserData object
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
        SwingUtilities.invokeLater(() -> new AdminHomepage(finalConnection, userData).setVisible(true));
    }
}
