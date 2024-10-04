package com.wms.ADMIN;

import com.wms.services.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import com.wms.models.UserData;

public class SettingsPage extends JFrame {
    private JTextField emailField, phoneField, addressField, firstnameField, lastnameField;
    private JPasswordField currentPasswordField, newPasswordField;
    private UserData userData;
    private UserService userService;

    public SettingsPage(UserData userData) throws SQLException {
        this.userData = userData;
        this.userService = new UserService();
        setTitle("Settings");
        setSize(900, 600);

        setLocationRelativeTo(null);

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/icons/settingslarge.png"));
                Image image = icon.getImage();

                int width = 50;
                int height = 50;

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
        imagePanel.setPreferredSize(new Dimension(50, 50));
        add(imagePanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Current Password
        JLabel currentPassLabel = new JLabel("Current Password:");
        c.gridx = 0; c.gridy = 0; panel.add(currentPassLabel, c);
        currentPasswordField = new JPasswordField(40);
        c.gridx = 1; panel.add(currentPasswordField, c);

        // First Name
        JLabel firstnameLabel = new JLabel("First Name:");
        c.gridx = 0; c.gridy = 1; panel.add(firstnameLabel, c);
        firstnameField = new JTextField(userData.getFirstname(), 40);
        c.gridx = 1; panel.add(firstnameField, c);

        // Last Name
        JLabel lastnameLabel = new JLabel("Last Name:");
        c.gridx = 0; c.gridy = 2; panel.add(lastnameLabel, c);
        lastnameField = new JTextField(userData.getLastname(), 40);
        c.gridx = 1; panel.add(lastnameField, c);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        c.gridx = 0; c.gridy = 3; panel.add(emailLabel, c);
        emailField = new JTextField(userData.getEmail(), 40);
        c.gridx = 1; panel.add(emailField, c);

        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        c.gridx = 0; c.gridy = 4; panel.add(phoneLabel, c);
        phoneField = new JTextField(userData.getPhone(), 40);
        c.gridx = 1; panel.add(phoneField, c);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        c.gridx = 0; c.gridy = 5; panel.add(addressLabel, c);
        addressField = new JTextField(userData.getAddress(), 40);
        c.gridx = 1; panel.add(addressField, c);



        // Save Button
        JButton saveButton = createStyleButton("Save Changes", "/icons/save.png");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPassword = new String(currentPasswordField.getPassword());
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();

                // Verify current password
                if (!userService.verifyPassword(userData.getUsername(), currentPassword)) {
                    JOptionPane.showMessageDialog(null, "Current password is incorrect!");
                    return;
                }

                // Update user data
                if (userService.updateUser(userData.getUsername(), email, phone, address, firstname, lastname)) {
                    JOptionPane.showMessageDialog(null, "Settings updated successfully!");
                    userData.setEmail(email); // Update local UserData
                    userData.setPhone(phone);
                    userData.setAddress(address);
                    userData.setFirstname(firstname);
                    userData.setLastname(lastname);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update settings.");
                }
            }
        });
        c.gridx = 1; c.gridy = 7; panel.add(saveButton, c);

        // Back Button
        JButton backButton = createStyleButton("Back", "/icons/back.png");
        backButton.addActionListener(e -> dispose());
        c.gridx = 0; panel.add(backButton, c);

        add(panel);
    }

    private JButton createStyleButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(255, 110, 110));
        button.setForeground(Color.WHITE);
        URL iconUrl = getClass().getResource(iconPath);
        if (iconUrl == null) {
            System.err.println("Icon not found: " + iconPath);
        } else {
            button.setIcon(new ImageIcon(iconUrl));
        }
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name", "null");
                new SettingsPage(userData).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
