package com.wms.CUSTOMER;

import com.wms.ADMIN.LoginFrame;

import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProductManagementPage extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton, backButton, logoutButton, saveButton, addButton, searchButton, sortButton;
    private JTextField searchField;
    private JComboBox<String> sortOptions;

    public ProductManagementPage() {
        setTitle("Product Management");
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

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.setForeground(Color.BLACK); // Black text

        // Search button
        searchButton = createStyledButton("Search", "icons/search.png");
        searchButton.addActionListener(e -> searchProducts());

        // Sort options
        sortOptions = new JComboBox<>(new String[]{
                "Sort By", "Category",
                "Quantity Ascending", "Quantity Descending",
                "Price Ascending", "Price Descending"
        });
        sortOptions.setFont(new Font("Arial", Font.PLAIN, 18));

        // Sort button
        sortButton = createStyledButton("Sort", "icons/sort.png");
        sortButton.addActionListener(e -> sortProducts());

        // Add search field, button, and sort options to the search panel
        searchGbc.gridx = 0;
        searchPanel.add(searchField, searchGbc);
        searchGbc.gridx = 1;
        searchPanel.add(searchButton, searchGbc);
        searchGbc.gridx = 2;
        searchPanel.add(sortOptions, searchGbc);
        searchGbc.gridx = 3;
        searchPanel.add(sortButton, searchGbc);

        // Add search panel to the grid
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        add(searchPanel, gbc);

        // Table setup
        String[] columnNames = {"Product Name", "Category", "Quantity", "Price", "Location"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 20));
        productTable.setForeground(Color.BLACK); // Black text

        productTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Customize table header
        JTableHeader header = productTable.getTableHeader();
        header.setBackground(new Color(255, 110, 110));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setReorderingAllowed(false);

        // Table cell alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to the grid
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE); // White background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Back button
        backButton = createStyledButton("Back", "icons/back.png");
        backButton.addActionListener(e -> dispose());

        // Logout button
        logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(e -> {
            dispose();
            try {
                new LoginFrame().setVisible(true); // Show the login page
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Refresh button
        refreshButton = createStyledButton("Refresh", "icons/refresh.png");
        refreshButton.addActionListener(e -> load());



        // Add buttons to the button panel
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.insets = new Insets(5, 5, 5, 5);

        btnGbc.gridx = 0;
        buttonPanel.add(backButton, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(refreshButton, btnGbc);
        btnGbc.gridx = 2;
        buttonPanel.add(logoutButton, btnGbc);

        // Add button panel to the bottom of the frame
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0.0;
        add(buttonPanel, gbc);

        load();
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Orange background
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setPreferredSize(new Dimension(200, 50));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Orange background
            }
        });
        return button;
    }

    private void sortProducts() {
        String selectedOption = (String) sortOptions.getSelectedItem();
        int columnIndex = -1;
        boolean ascending = true;

        switch (selectedOption) {
            case "Category":
                columnIndex = 1;
                ascending = true;
                break;
            case "Quantity Ascending":
                columnIndex = 2;
                ascending = true;
                break;
            case "Quantity Descending":
                columnIndex = 2;
                ascending = false;
                break;
            case "Price Ascending":
                columnIndex = 3;
                ascending = true;
                break;
            case "Price Descending":
                columnIndex = 3;
                ascending = false;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Please select a sorting option.");
                return;
        }
        sortTable(columnIndex, ascending);
    }

    private void sortTable(int columnIndex, boolean ascending) {
        // Custom sorting logic for the table
        tableModel.getDataVector().sort((o1, o2) -> {
            Comparable value1 = (Comparable) ((Vector) o1).get(columnIndex);
            Comparable value2 = (Comparable) ((Vector) o2).get(columnIndex);
            return ascending ? value1.compareTo(value2) : value2.compareTo(value1);
        });
        tableModel.fireTableDataChanged(); // Notify the table to refresh
    }
    private void load() {
        String query = "SELECT p.product_name, c.category_name, p.quantity, p.price, p.location " +
                "FROM products p LEFT JOIN category c ON p.category_id = c.category_id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            tableModel.setRowCount(0); // Clear existing data in the table

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                String category = resultSet.getString("category_name");
                int quantity = resultSet.getInt("quantity");
                float price = resultSet.getFloat("price");
                String location = resultSet.getString("location");
                tableModel.addRow(new Object[]{productName, category, quantity, price, location});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void searchProducts() {
        String searchTerm = searchField.getText();
        String query = "SELECT p.product_name, c.category_name, p.quantity, p.price, p.location " +
                "FROM products p LEFT JOIN category c ON p.category_id = c.category_id " +
                "WHERE p.product_name LIKE ? OR c.category_name LIKE ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            tableModel.setRowCount(0); // Clear existing data in the table

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                String category = resultSet.getString("category_name");
                int quantity = resultSet.getInt("quantity");
                float price = resultSet.getFloat("price");
                String location = resultSet.getString("location");
                tableModel.addRow(new Object[]{productName, category, quantity, price, location});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductManagementPage().setVisible(true));
    }
}

