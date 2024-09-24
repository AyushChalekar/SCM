package com.wms.gui;

import com.wms.services.UserService;
import com.wms.models.User;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import com.wms.gui.SignUpFrame;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.SalesPage;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserService userService;
    String ipAddress = getIPAddress(); // Get the user's IP address

    public LoginFrame() throws SQLException {
        userService = new UserService();

        setTitle("Login - Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set background color to warm black
        getContentPane().setBackground(new Color(40, 40, 40));

        // Set layout manager
        setLayout(new BorderLayout());

        // Left panel for the image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(40, 40, 40)); // Same background color
        JLabel imageLabel = new JLabel(new ImageIcon("C:\\Users\\bloxe\\Documents\\windows 10\\Videos\\vector\\SCMLOGO.jpg")); // Replace with your image path
        imagePanel.add(imageLabel);

        // Right panel for login form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(40, 40, 40)); // Same background color
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("Smart Cargo Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(titleLabel, c);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        userLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        panel.add(userLabel, c);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 1;
        panel.add(usernameField, c);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(passLabel, c);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(passwordField, c);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 1;
        c.gridy = 3;
        panel.add(loginButton, c);

        JLabel signUpLabel = new JLabel("Don't have an account?");
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpLabel.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(signUpLabel, c);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 1;
        c.gridy = 4;
        panel.add(signUpButton, c);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userService.authenticateUser(username, password)) {
                    User user = userService.getUserByUsername(username);
                    userService.logUserLogin(user.getUserId(), ipAddress);
                    switch (user.getRole()) {
                        case "Admin":
                           // new SalesPage().setVisible(true);
                            try {
                                Connection conn = DatabaseConnection.getConnection();
                                new AdminHomepage(conn).setVisible(true);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            break;

                        case "Employee":
                            new EmployeesPage().setVisible(true);
                            break;

                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // new SignUpFrame().setVisible(true); // Open the Sign-Up Frame
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    new SignUpFrame().setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                dispose(); // Close the Login Frame
            }
        });

        // Adding components to the frame
        add(imagePanel, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
    }

    private String getIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            return "Unknown";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new LoginFrame().setVisible(true);
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    new LoginFrame().setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
