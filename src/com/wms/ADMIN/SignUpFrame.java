package com.wms.ADMIN;

import com.wms.services.UserService;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class SignUpFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JComboBox<String> roleComboBox;
    private UserService userService;

    public SignUpFrame() throws SQLException {
        userService = new UserService();

        setTitle("Sign Up - Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set background color for the main frame
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Left panel for the image
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png"));
                Image image = icon.getImage();

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
        imagePanel.setPreferredSize(new Dimension(400, getHeight())); // Ensuring the panel has enough width
        imagePanel.setBackground(new Color(255, 110, 110)); // Set left panel color to orange

        // Right panel for signup form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.WEST; // Align components to the left

        JLabel titleLabel = new JLabel("Sign Up - Warehouse Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 110, 110));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(titleLabel, c);

        // Dropdown for role selection (Combo Box) placed right below the title
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        roleLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(roleLabel, c);

        String[] roles = {"Customer", "Delivery Partner"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 1;
        panel.add(roleComboBox, c);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        userLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(userLabel, c);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 2;
        panel.add(usernameField, c);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(passLabel, c);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 3;
        panel.add(passwordField, c);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        emailLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(emailLabel, c);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 4;
        panel.add(emailField, c);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 5;
        panel.add(phoneLabel, c);

        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 5;
        panel.add(phoneField, c);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        addressLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 6;
        panel.add(addressLabel, c);

        addressField = new JTextField(20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 6;
        panel.add(addressField, c);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        signUpButton.setBackground(new Color(255, 110, 110));
        signUpButton.setForeground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 7;
        panel.add(signUpButton, c);

        JLabel alreadyHaveAccountLabel = new JLabel("Already have an account?");
        alreadyHaveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        alreadyHaveAccountLabel.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 8;
        panel.add(alreadyHaveAccountLabel, c);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(new Color(255, 110, 110));
        loginButton.setForeground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 8;
        panel.add(loginButton, c);

        // Action listener for Sign Up button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                // Call the UserService register method
                if (userService.registerUser(username, password, email, phone, address, role)) {
                    JOptionPane.showMessageDialog(null, "Sign Up Successful");
                    try {
                        Connection conn = DatabaseConnection.getConnection();
                        new LoginFrame().setVisible(true);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } // Open login page after successful sign up
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Sign Up Failed");
                }
            }
        });

        // Action listener for Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    new LoginFrame().setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }
        });

        // Adding components to the frame
        add(imagePanel, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    new SignUpFrame().setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
