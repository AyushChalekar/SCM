package com.deliverytracker.gui;

import com.deliverytracker.services.UserService;
import com.deliverytracker.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JLabel signUpPrompt;
    private UserService userService; // Instance variable for UserService

    public LoginFrame() {
        userService = new UserService(); // Initialize UserService instance

        setTitle("Login");
        setSize(800, 600); // You can adjust the width (800) and height (600) as needed

        // Optionally, to start maximized:
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Ensure the frame appears at the center of the screen
        setLocationRelativeTo(null);

        // Make the frame visible
        setVisible(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        signUpPrompt = new JLabel("Don't have an account?");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(signUpPrompt, gbc);

        gbc.gridx = 1;
        add(signUpButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Use the instance variable to call methods
                if (userService.authenticateUser(username, password)) {
                    User user = userService.getUserByUsername(username);
                    if (user != null) {
                        // Open the appropriate frame based on user role
                        switch (user.getRole()) {
                            case "admin":
                                new AdminFrame(); // Open Admin frame
                                break;
                            case "delivery_partner":
                                new DeliveryPartnerFrame(); // Open Delivery Partner frame
                                break;
                            case "user":
                                new UserFrame(); // Open User frame
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid role.");
                                break;
                        }
                        dispose(); // Close the login frame
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpFrame(); // Open Sign Up frame
                dispose(); // Close the login frame
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
