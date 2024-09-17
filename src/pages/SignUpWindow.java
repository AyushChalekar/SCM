package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class SignUpWindow extends javax.swing.JFrame {
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


    public SignUpWindow() {
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        roleLabel = new javax.swing.JLabel();
        roleComboBox = new javax.swing.JComboBox<>();
        signupButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(4, 2));

        usernameLabel.setText("Username:");
        jPanel1.add(usernameLabel);
        jPanel1.add(usernameTextField);

        passwordLabel.setText("Password");
        jPanel1.add(passwordLabel);

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        jPanel1.add(passwordField);

        roleLabel.setText("Role");
        jPanel1.add(roleLabel);

        roleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Warehouse Admin", "Buyer", "Supplier" }));
        roleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(roleComboBox);

        signupButton.setText("Signup");
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });
        jPanel1.add(signupButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
    String username = usernameTextField.getText();
    String password = new String(passwordField.getPassword());
    String role = (String) roleComboBox.getSelectedItem();

    if (validateUsername(username)) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scm", "root", "root")) {
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password); // Consider hashing the password in a real application
                pstmt.setString(3, role);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                new LoginWindow().setVisible(true);
                this.dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Username must only contain letters and numbers.", "Invalid Username", JOptionPane.WARNING_MESSAGE);
    }
}

private boolean validateUsername(String username) {
    // Regex to allow only letters and numbers
    return username.matches("[a-zA-Z0-9]+");
    }//GEN-LAST:event_signupButtonActionPerformed

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

    private void roleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roleComboBoxActionPerformed
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(SignUpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUpWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUpWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JComboBox<String> roleComboBox;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JButton signupButton;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
