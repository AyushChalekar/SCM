package com.deliverytracker.gui;

import com.deliverytracker.services.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class SignUpFrame extends JFrame {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel roleLabel;
    private JComboBox<String> roleComboBox;
    private JButton signUpButton;
    private JButton loginButton;
    private JLabel loginPrompt;
    private UserService userService;

    public SignUpFrame() {
        userService = new UserService(); // Initialize UserService instance

        setTitle("Sign Up");
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
        roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[] { "user", "delivery_partner" });
        signUpButton = new JButton("Sign Up");
        loginButton = new JButton("Login");
        loginPrompt = new JLabel("Already have an account?");

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
        add(roleLabel, gbc);

        gbc.gridx = 1;
        add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(loginPrompt, gbc);

        gbc.gridx = 1;
        add(loginButton, gbc);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                if (userService.isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(null, "Username is already taken.");
                } else if (userService.createUser(username, password, role)) {
                    JOptionPane.showMessageDialog(null, "User created successfully.");
                    new LoginFrame(); // Open Login frame
                    dispose(); // Close the sign-up frame
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating user.");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(); // Open Login frame
                dispose(); // Close the sign-up frame
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
