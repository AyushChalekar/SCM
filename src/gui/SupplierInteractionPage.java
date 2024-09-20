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
import javax.swing.table.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class SupplierInteractionPage extends JFrame {
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, backButton, logoutButton, saveButton, addButton;

    public SupplierInteractionPage() {
        setTitle("Supplier Interaction");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.BLACK); // Warm black background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 2, 10, 2); // Padding

        // Table setup
        String[] columnNames = {"Username", "Email", "IP Address"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Make all cells editable
            }
        };
        supplierTable = new JTable(tableModel);
        supplierTable.setFont(new Font("Arial", Font.PLAIN, 20)); // Large fonts
        supplierTable.setForeground(Color.WHITE);
        supplierTable.setBackground(Color.DARK_GRAY);
        supplierTable.setRowHeight(30); // Increased row height for better readability
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Customize table header
        JTableHeader header = supplierTable.getTableHeader();
        header.setBackground(Color.GRAY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setReorderingAllowed(false);

        // Table cell alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < supplierTable.getColumnCount(); i++) {
            supplierTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set column widths
        TableColumnModel columnModel = supplierTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // Username
        columnModel.getColumn(1).setPreferredWidth(300); // Email
        columnModel.getColumn(2).setPreferredWidth(150); // IP Address

        // Add table to the grid
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Span all columns
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridBagLayout());
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
                dispose(); // Close the current frame (example action)
            }
        });

        // Refresh button
        refreshButton = createStyledButton("Refresh Suppliers", "icons/refresh.png");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSuppliers();
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
        addButton = createStyledButton("Add Supplier", "icons/add.png");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt for new supplier details
                String username = JOptionPane.showInputDialog("Enter Username:");
                String email = JOptionPane.showInputDialog("Enter Email:");
                String ipAddress = JOptionPane.showInputDialog("Enter IP Address:");

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
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.0;
        add(buttonPanel, gbc);

        loadSuppliers();
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

        // Adjust button size to ensure text fits
        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        )); // Rounded corners with shadow

        // Button animation
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

    private void loadSuppliers() {
        String query = "SELECT username, email, ip_address FROM users WHERE role_id = (SELECT role_id FROM roles WHERE role_name = 'Supplier')";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Clear existing data in the table
            tableModel.setRowCount(0);

            // Process resultSet to populate supplier data
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String ipAddress = resultSet.getString("ip_address");
                tableModel.addRow(new Object[]{username, email, ipAddress});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveChanges() {
        int rowCount = tableModel.getRowCount();
        String updateQuery = "UPDATE users SET email = ?, ip_address = ? WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            for (int i = 0; i < rowCount; i++) {
                String username = (String) tableModel.getValueAt(i, 0);
                String email = (String) tableModel.getValueAt(i, 1);
                String ipAddress = (String) tableModel.getValueAt(i, 2);

                // Update each row
                updateStatement.setString(1, email);
                updateStatement.setString(2, ipAddress);
                updateStatement.setString(3, username);
                updateStatement.addBatch();
            }
            updateStatement.executeBatch();

            JOptionPane.showMessageDialog(this, "Changes saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save changes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupplierInteractionPage().setVisible(true));
    }
}
