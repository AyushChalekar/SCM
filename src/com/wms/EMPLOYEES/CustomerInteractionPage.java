package com.wms.EMPLOYEES;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wms.ADMIN.LoginFrame;

public class CustomerInteractionPage extends JFrame {
    private JTable CustomerTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, backButton, logoutButton, saveButton, addButton, searchButton;
    private JTextField searchField;

    public CustomerInteractionPage() {
        setTitle("Customer Interaction");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE); // White background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 2, 10, 2); // Padding

        // Search panel setup
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.WHITE); // White background

        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(5, 5, 5, 5); // Add padding for better spacing
        searchGbc.fill = GridBagConstraints.HORIZONTAL;

        // Search field
        searchField = new JTextField(20); // Limit the size to make it smaller
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.setForeground(Color.BLACK); // Black text

        // Search button
        searchButton = createStyledButton("Search", "icons/search.png");
        searchButton.addActionListener(e -> searchSuppliers());

        // Add search field and button to the search panel
        searchGbc.gridx = 0;
        searchPanel.add(searchField, searchGbc);

        searchGbc.gridx = 1;
        searchPanel.add(searchButton, searchGbc);

        // Add search panel to the grid
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Reduce the width it takes on the layout
        gbc.weightx = 0.0; // Adjust weight so it doesn’t stretch
        gbc.weighty = 0.0;
        add(searchPanel, gbc);

        // Table setup
        String[] columnNames = {"Username", "Email", "Contact"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Make all cells editable
            }
        };
        CustomerTable = new JTable(tableModel);
        CustomerTable.setFont(new Font("Arial", Font.PLAIN, 20)); // Large fonts
        CustomerTable.setForeground(Color.BLACK); // Black text
        CustomerTable.setBackground(Color.WHITE); // White background
        CustomerTable.setRowHeight(30); // Increased row height for better readability
        JScrollPane scrollPane = new JScrollPane(CustomerTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Customize table header
        JTableHeader header = CustomerTable.getTableHeader();
        header.setBackground(new Color(255, 110, 110));
        header.setForeground(Color.BLACK); // Black text
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setReorderingAllowed(false);

        // Table cell alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < CustomerTable.getColumnCount(); i++) {
            CustomerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set column widths
        TableColumnModel columnModel = CustomerTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // Username
        columnModel.getColumn(1).setPreferredWidth(300); // Email
        columnModel.getColumn(2).setPreferredWidth(150); // Contact

        // Add table to the grid
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE); // White background
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding

        // Back button
        backButton = createStyledButton("Back", "icons/back.png");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
            }
        });

        // Logout button
        logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(e -> {
            dispose(); // Close the Admin Homepage
            try {
                new LoginFrame().setVisible(true); // Show the login page
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Refresh button
        refreshButton = createStyledButton("Refresh", "icons/refresh.png");
        refreshButton.addActionListener(e -> load());

        // Save button
        saveButton = createStyledButton("Save Changes", "icons/save.png");
        saveButton.addActionListener(e -> saveChanges());

        // Add button
        addButton = createStyledButton("Create", "icons/add.png");
        addButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter Username:");
            String email = JOptionPane.showInputDialog("Enter Email:");
            String contact_no = JOptionPane.showInputDialog("Enter Contact no.:");

            if (username != null && email != null && contact_no != null) {
                tableModel.addRow(new Object[]{username, email, contact_no});
            }
        });

        // Add buttons to the button panel
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.insets = new Insets(5, 5, 5, 5);

        btnGbc.gridx = 0;
        btnGbc.gridy = 0;
        buttonPanel.add(backButton, btnGbc);

        btnGbc.gridx = 4;
        buttonPanel.add(logoutButton, btnGbc);

        btnGbc.gridx = 3;
        buttonPanel.add(refreshButton, btnGbc);

        btnGbc.gridx = 1;
        buttonPanel.add(saveButton, btnGbc);

        btnGbc.gridx = 2;
        buttonPanel.add(addButton, btnGbc);

        // Add button panel to the bottom of the frame
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.0;
        add(buttonPanel, gbc);

        load();
    }


    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Orange color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        return button;
    }
    private void load() {
        String query = "SELECT username, email, contact_no FROM users WHERE role_id = (SELECT role_id FROM roles WHERE role_name = 'Customer')";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Clear existing data in the table
            tableModel.setRowCount(0);

            // Process resultSet to populate supplier data
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String contact_no = resultSet.getString("contact_no");
                tableModel.addRow(new Object[]{username, email, contact_no});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveChanges() {
        int rowCount = tableModel.getRowCount();
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, email, contact_no, role_id) VALUES (?, ?, ?, (SELECT role_id FROM roles WHERE role_name = 'Customer'))";
        String updateQuery = "UPDATE users SET email = ?, contact_no = ? WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            for (int row = 0; row < rowCount; row++) {
                String username = (String) tableModel.getValueAt(row, 0);
                String email = (String) tableModel.getValueAt(row, 1);
                String contact_no = (String) tableModel.getValueAt(row, 2);

                // Check if the supplier already exists
                checkStatement.setString(1, username);
                ResultSet rs = checkStatement.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    // If supplier exists, update their information
                    updateStatement.setString(1, email);
                    updateStatement.setString(2, contact_no);
                    updateStatement.setString(3, username);
                    updateStatement.addBatch();
                } else {
                    // If supplier is new, insert their information
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, email);
                    insertStatement.setString(3, contact_no);
                    insertStatement.addBatch();
                }
            }

            // Execute batch update and insert commands
            updateStatement.executeBatch();
            insertStatement.executeBatch();

            JOptionPane.showMessageDialog(this, "Customer data saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void searchSuppliers() {
        String searchText = searchField.getText();
        String searchQuery = "SELECT username, email, contact_no FROM users WHERE (username LIKE ? OR email LIKE ?) AND role_id = (SELECT role_id FROM roles WHERE role_name = 'Customer')";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement searchStatement = connection.prepareStatement(searchQuery)) {

            searchStatement.setString(1, "%" + searchText + "%");
            searchStatement.setString(2, "%" + searchText + "%");

            try (ResultSet resultSet = searchStatement.executeQuery()) {
                tableModel.setRowCount(0); // Clear existing rows

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String contact_no = resultSet.getString("contact_no");
                    tableModel.addRow(new Object[]{username, email, contact_no});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerInteractionPage().setVisible(true));
    }
}
