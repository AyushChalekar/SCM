package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wms.utils.DatabaseConnection;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import javax.swing.table.TableColumnModel;

public class DeliverPartnerInteractionPage extends JFrame {
    private JTable CustomerTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, backButton, logoutButton, saveButton, addButton, searchButton;
    private JTextField searchField;

    public DeliverPartnerInteractionPage() {
        setTitle("Delivery Interaction");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.BLACK); // Warm black background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 2, 10, 2); // Padding

        // Adjust search panel to be smaller and centered
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.BLACK);

        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(5, 5, 5, 5); // Add padding for better spacing
        searchGbc.fill = GridBagConstraints.HORIZONTAL;

// Search field (smaller size)
        searchField = new JTextField(20); // Limit the size to make it smaller
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));

// Search button
        searchButton = createStyledButton("Search", "icons/search.png");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSuppliers();
            }
        });

// Add search field and button to the search panel with new constraints
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
        CustomerTable.setForeground(Color.WHITE);
        CustomerTable.setBackground(Color.DARK_GRAY);
        CustomerTable.setRowHeight(30); // Increased row height for better readability
        JScrollPane scrollPane = new JScrollPane(CustomerTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Customize table header
        JTableHeader header = CustomerTable.getTableHeader();
        header.setBackground(Color.GRAY);
        header.setForeground(Color.WHITE);
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
        columnModel.getColumn(2).setPreferredWidth(150); // IP Address

        // Add table to the grid
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding

        // Back button with icon
        backButton = createStyledButton("Back", "icons/back.png");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame (example action)
            }
        });

        // Logout button with icon
        logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(new ActionListener() {
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
        // Refresh button
        refreshButton = createStyledButton("Refresh", "icons/refresh.png");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });

        // Save button
        saveButton = createStyledButton("Save Changes", "icons/save.png");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        // Add button
        addButton = createStyledButton("Create", "icons/add.png");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt for new supplier details
                String username = JOptionPane.showInputDialog("Enter Username:");
                String email = JOptionPane.showInputDialog("Enter Email:");
                String ipAddress = JOptionPane.showInputDialog("Enter Contact no.:");

                if (username != null && email != null && ipAddress != null) {
                    tableModel.addRow(new Object[]{username, email, ipAddress});
                }
            }
        });

        // Add buttons to the button panel
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.insets = new Insets(5, 5, 5, 5);

        // Back button
        btnGbc.gridx = 0;
        btnGbc.gridy = 0;
        buttonPanel.add(backButton, btnGbc);

        // Logout button
        btnGbc.gridx = 4;
        buttonPanel.add(logoutButton, btnGbc);

        // Refresh button
        btnGbc.gridx = 3;
        buttonPanel.add(refreshButton, btnGbc);

        // Save button
        btnGbc.gridx = 1;
        buttonPanel.add(saveButton, btnGbc);

        // Add new row button
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
        button.setBackground(Color.GRAY);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        )); // Rounded corners with shadow

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.GRAY);
            }
        });

        return button;
    }

    private void load() {
        String query = "SELECT username, email, contact_no FROM users WHERE role_id = (SELECT role_id FROM roles WHERE role_name = 'Delivery Partner')";
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
        String insertQuery = "INSERT INTO users (username, email, contact_no, role_id) VALUES (?, ?, ?, (SELECT role_id FROM roles WHERE role_name = 'Delivery Partner'))";
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
        String searchQuery = "SELECT username, email, contact_no FROM users WHERE (username LIKE ? OR email LIKE ?) AND role_id = (SELECT role_id FROM roles WHERE role_name = 'Delivery Partner')";

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
        SwingUtilities.invokeLater(() -> new DeliverPartnerInteractionPage().setVisible(true));
    }
}
