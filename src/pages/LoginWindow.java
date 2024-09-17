package pages;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginWindow extends javax.swing.JFrame {
    public static final String DB_URL="jdbc:mysql://localhost:3306/scm";
    public static final String DB_USER="root";
    public static final String DB_PASSWORD="root";
    
    private boolean validateUser(String username, char[] password){
         String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
         
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, username);
        stmt.setString(2, new String(password)); // Convert char[] to String
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count > 0
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle exceptions
    }
    return false;
    }
    
//    public boolean isValidPassword(char[] password) {
//    String pass = new String(password);  // Convert char[] to String
//    if (pass.length() < 8) {
//        return false;  // Password is too short
//    }
//
//    boolean hasUppercase = false;
//    boolean hasLowercase = false;
//    boolean hasDigit = false;
//    boolean hasSpecialChar = false;
//
//    for (char ch : pass.toCharArray()) {
//        if (Character.isUpperCase(ch)) {
//            hasUppercase = true;
//        } else if (Character.isLowerCase(ch)) {
//            hasLowercase = true;
//        } else if (Character.isDigit(ch)) {
//            hasDigit = true;
//        } else if (!Character.isLetterOrDigit(ch)) {
//            hasSpecialChar = true;
//        }
//    }
//
//    // Password must have at least one of each type: uppercase, lowercase, digit, and special character
//    return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
//}

    public LoginWindow() {
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        signupButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");

        loginPanel.setMinimumSize(new java.awt.Dimension(150, 75));
        loginPanel.setPreferredSize(new java.awt.Dimension(300, 150));
        loginPanel.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        usernameLabel.setText("Username:");
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);

        passwordLabel.setText("Password:");
        passwordLabel.setMaximumSize(new java.awt.Dimension(50, 60));
        passwordLabel.setMinimumSize(new java.awt.Dimension(50, 60));
        loginPanel.add(passwordLabel);

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        loginPanel.add(passwordField);

        signupButton.setText("Signup");
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });
        loginPanel.add(signupButton);

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        loginPanel.add(loginButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (validateUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            home homePage = new home();
            homePage.setVisible(true);
            
            this.dispose();
            
        } else {
            // Show an error message
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
        Arrays.fill(password, ' ');
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed

//    char[] password = passwordField.getPassword();
//    if (isValidPassword(password)) {
//        // Proceed with login
//    } else {
//            javax.swing.JOptionPane.showMessageDialog(this, 
//            "Invalid password! It must contain at least 8 characters, including uppercase, lowercase, digits, and special characters.",
//            "Password Error",
//            javax.swing.JOptionPane.ERROR_MESSAGE);}
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
            java.awt.EventQueue.invokeLater(() -> {
                new SignUpWindow().setVisible(true);
            });
    }//GEN-LAST:event_signupButtonActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        new LoginWindow().setVisible(true);
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoginWindow().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton signupButton;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}