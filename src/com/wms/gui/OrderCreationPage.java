package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class OrderCreationPage extends JFrame {

    private JTextField searchField;
    private JComboBox<String> sortOptions;
    private JTextField usernameField, productIdField, productDetailsField, quantityField;
    private JButton placeOrderButton, backButton, logoutButton, saveButton, searchButton, sortbutton;
    private JTable ordersTable;
    private DefaultTableModel ordersTableModel;

    public OrderCreationPage() {
        setTitle("Create Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout());

        // Top Panel for Search and Sort
        JPanel searchSortPanel = new JPanel(new GridBagLayout());
        searchSortPanel.setBackground(Color.WHITE);
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.BOLD, 24));

        String[] sortChoices = {"Order ID (ASC)", "Order ID (DESC)", "Username (ASC)", "Username (DESC)"};
        sortOptions = new JComboBox<>(sortChoices);
        sortOptions.setFont(new Font("Arial", Font.BOLD, 24));

        searchButton = createStyledButton("Search", "icons/search.png");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 18));
        searchButton.addActionListener(e -> searchOrders());
        sortbutton = createStyledButton("Sort", "icons/search.png");
        sortbutton.setFont(new Font("Arial", Font.PLAIN, 18));
        sortbutton.addActionListener(e -> sortOrders());
        GridBagConstraints searchSortGbc = new GridBagConstraints();
        searchSortGbc.fill = GridBagConstraints.HORIZONTAL;
        searchSortGbc.insets = new Insets(5, 5, 5, 5);
        searchSortGbc.gridx = 0;
        searchSortGbc.gridy = 0;
        searchSortPanel.add(searchField, searchSortGbc);
        searchSortGbc.gridx = 1;
        searchSortPanel.add(searchButton, searchSortGbc);
        searchSortGbc.gridx = 2;
        searchSortPanel.add(sortOptions, searchSortGbc);
        searchSortGbc.gridx = 3;
        searchSortPanel.add(sortbutton, searchSortGbc);

        // Add searchSortPanel to the top of the main layout
        getContentPane().add(searchSortPanel, BorderLayout.NORTH);

        // Create Order Panel on the right
        JPanel orderCreationPanel = new JPanel(new GridBagLayout());
        orderCreationPanel.setBackground(Color.WHITE);
        orderCreationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setForeground(Color.BLACK);
        productIdLabel.setFont(new Font("Arial", Font.BOLD, 24));
        productIdField = new JTextField(10);
        productIdField.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel productDetailsLabel = new JLabel("Product Details:");
        productDetailsLabel.setForeground(Color.BLACK);
        productDetailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        productDetailsField = new JTextField(10);
        productDetailsField.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.BLACK);
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        quantityField = new JTextField(10);
        quantityField.setFont(new Font("Arial", Font.BOLD, 24));

        placeOrderButton = createStyledButton("Place Order", "icons/place_order.png");
        placeOrderButton.setFont(new Font("Arial", Font.BOLD, 24));
        placeOrderButton.addActionListener(e -> placeOrder());

        GridBagConstraints creationGbc = new GridBagConstraints();
        creationGbc.fill = GridBagConstraints.HORIZONTAL;
        creationGbc.insets = new Insets(5, 5, 5, 5);
        creationGbc.gridx = 0;
        creationGbc.gridy = 0;
        orderCreationPanel.add(usernameLabel, creationGbc);
        creationGbc.gridx = 1;
        orderCreationPanel.add(usernameField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 1;
        orderCreationPanel.add(productIdLabel, creationGbc);
        creationGbc.gridx = 1;
        orderCreationPanel.add(productIdField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 2;
        orderCreationPanel.add(productDetailsLabel, creationGbc);
        creationGbc.gridx = 1;
        orderCreationPanel.add(productDetailsField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 3;
        orderCreationPanel.add(quantityLabel, creationGbc);
        creationGbc.gridx = 1;
        orderCreationPanel.add(quantityField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 4;
        creationGbc.gridwidth = 2;
        orderCreationPanel.add(placeOrderButton, creationGbc);

        // Add the orderCreationPanel to the right of the main layout
        getContentPane().add(orderCreationPanel, BorderLayout.EAST);

        // Orders Table in the center
        String[] orderColumns = {"Order ID", "Username", "Product ID", "Product Details", "Quantity", "Status", "Tracking ID", "Order Details"};
        ordersTableModel = new DefaultTableModel(orderColumns, 0);
        ordersTable = new JTable(ordersTableModel);
        styleTable(ordersTable);
        JScrollPane orderScrollPane = new JScrollPane(ordersTable);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderScrollPane.setPreferredSize(new Dimension(800, 600));

        getContentPane().add(orderScrollPane, BorderLayout.CENTER);

        // Bottom Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered with padding

        backButton = createStyledButton("Back", "icons/back.png");
        backButton.addActionListener(e -> dispose());
        logoutButton = createStyledButton("Logout", "icons/logout.png");
        logoutButton.addActionListener(e -> {
            dispose();
            try {
                new LoginFrame().setVisible(true);  // Return to login page
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        saveButton = createStyledButton("Save", "icons/save.png");
        saveButton.addActionListener(e -> saveChanges());

        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(logoutButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadOrdersData();
    }

    // Search orders based on the input in the searchField
    private void searchOrders() {
        String searchText = searchField.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(ordersTableModel);
        ordersTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
    }

    // Sort orders based on the selected option in sortOptions
    private void sortOrders() {
        String selectedOption = (String) sortOptions.getSelectedItem();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(ordersTableModel);
        ordersTable.setRowSorter(sorter);

        if (selectedOption != null) {
            switch (selectedOption) {
                case "Order ID (ASC)":
                    sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                    break;
                case "Order ID (DESC)":
                    sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
                    break;
                case "Username (ASC)":
                    sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                    break;
                case "Username (DESC)":
                    sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.DESCENDING)));
                    break;
            }
        }
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setGridColor(Color.GRAY);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(255, 110, 110)); // Custom orange color
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    private void loadOrdersData() {
        // This method would connect to the database and retrieve orders data
    }

    private void placeOrder() {
        // This method would handle placing an order
    }

    private void saveChanges() {
        // This method would handle saving changes to the database
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderCreationPage frame = new OrderCreationPage();
            frame.setVisible(true);
        });
    }
}
