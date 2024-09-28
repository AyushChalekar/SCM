package com.wms.gui;

import com.wms.models.UserData;
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

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserService userService;
    String ipAddress = getIPAddress();

    public LoginFrame() throws SQLException {
        userService = new UserService();

        setTitle("Login - Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Background panel with an image
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("D:\\SCM-main\\res\\loginpanel.png");
                Image image = imageIcon.getImage();
                int width = getWidth();
                int height = getHeight();

                // Preserve aspect ratio of the image
                int imageWidth = image.getWidth(null);
                int imageHeight = image.getHeight(null);
                float aspectRatio = (float) imageWidth / imageHeight;

                if (width > height) {
                    width = (int) (height * aspectRatio);
                } else {
                    height = (int) (width / aspectRatio);
                }

                // Center the image
                int x = (getWidth() - width) / 2;
                int y = (getHeight() - height) / 2;

                g.drawImage(image, x, y, width, height, this);
            }
        };
        imagePanel.setPreferredSize(new Dimension(400, getHeight()));
        imagePanel.setBackground(new Color(255, 110, 110)); // Set left panel background to orange

        // Login form panel (no rounded edges)
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("Smart Cargo Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 110, 110));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        loginPanel.add(titleLabel, c);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        userLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        loginPanel.add(userLabel, c);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 1;
        loginPanel.add(usernameField, c);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 2;
        loginPanel.add(passLabel, c);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 2;
        loginPanel.add(passwordField, c);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(new Color(255, 110, 110));
        loginButton.setForeground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 3;
        loginPanel.add(loginButton, c);

        JLabel signUpLabel = new JLabel("Don't have an account?");
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 4;
        loginPanel.add(signUpLabel, c);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        signUpButton.setBackground(new Color(255, 110, 110));
        signUpButton.setForeground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 4;
        loginPanel.add(signUpButton, c);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = userService.getUserByUsername(username);
                if (user != null && userService.authenticateUser(username, password)) {
                    userService.logUserLogin(user.getUserId(), ipAddress);

                    UserData userData = new UserData(user.getUsername(), user.getRole()); // Create UserData instance
                    try {
                        Connection conn = DatabaseConnection.getConnection();
                        new AdminHomepage(conn).setVisible(true); // Pass UserData to AdminHomepage
                    } catch (SQLException e1) {
                        e1.printStackTrace();
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
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    new SignUpFrame().setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }
        });

        // Add components to the frame
        add(imagePanel, BorderLayout.WEST);
        add(loginPanel, BorderLayout.CENTER);
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