package com.wms.EMPLOYEES;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.List;
import com.wms.ADMIN.LoginFrame;

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
        sortbutton = createStyledButton("Sort", "icons/sort.png");
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
        header.setBackground(new Color(255, 110, 110));
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

        button.setPreferredSize(new Dimension(200, 50));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        return button;
    }

    private void saveChanges() {
        int rowCount = ordersTableModel.getRowCount();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.200:3306/wms1", "LAP", "root")) {
            for (int i = 0; i < rowCount; i++) {
                int orderId = (int) ordersTableModel.getValueAt(i, 0); // Assuming Order ID is in the first column
                int productId = (int) ordersTableModel.getValueAt(i, 2); // Product ID in the third column
                String productDetails = (String) ordersTableModel.getValueAt(i, 3); // Product details in the fourth column
                int quantity = (int) ordersTableModel.getValueAt(i, 4); // Quantity in the fifth column
                String status = (String) ordersTableModel.getValueAt(i, 5); // Status in the sixth column
                String trackingId = (String) ordersTableModel.getValueAt(i, 6); // Tracking ID in the seventh column
                String orderdeatils = (String) ordersTableModel.getValueAt(i, 7); // Order details in the eighth column

                String updateSQL = "UPDATE orders SET status = ?, order_deatils = ?,product_id = ?,tracking_id = ?,product_details = ?,quantity = ? WHERE order_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
                    stmt.setString(1, status);
                    stmt.setString(2, orderdeatils);
                    stmt.setInt(3, productId);
                    stmt.setString(4, trackingId);
                    stmt.setString(5, productDetails);
                    stmt.setInt(6, quantity);
                    stmt.setInt(7, orderId);
                    stmt.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(this, "Changes saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving changes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadOrdersData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.200:3306/wms1", "LAP", "root");
             PreparedStatement stmt = conn.prepareStatement("SELECT o.order_id, u.username, o.product_id,o.product_details,  o.quantity, o.status, o.tracking_id, o.order_deatils " +
                     "FROM orders o JOIN users u ON o.user_id = u.user_id " +
                     "WHERE o.status NOT IN ('Delivered', 'Canceled');");
             ResultSet rs = stmt.executeQuery()) {

            // Clear existing data
            ordersTableModel.setRowCount(0);

            // Add data to the table
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String username = rs.getString("username");
                int productId = rs.getInt("product_id");
                String product_details = rs.getString("product_details");
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");
                String trackingId = rs.getString("tracking_id");
                String order_deatils = rs.getString("order_deatils");
                ordersTableModel.addRow(new Object[]{orderId, username, productId,product_details, quantity, status, trackingId, order_deatils});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void placeOrder() {
        String username = usernameField.getText();
        String productIdStr = productIdField.getText();
        String quantityStr = quantityField.getText();
        String productDetails = productDetailsField.getText();

        if (username.isEmpty() || productIdStr.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.200:3306/wms1", "LAP", "root")) {
            // Retrieve user_id based on username
            PreparedStatement userStmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            userStmt.setString(1, username);
            ResultSet userResult = userStmt.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("user_id");
                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);

                // Generate a unique tracking ID with small letters and numbers
                String trackingId = generateUniqueTrackingId();

                // Insert order into the database
                String insertOrderSQL = "INSERT INTO orders (user_id, product_id, quantity, status, tracking_id, product_details) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertOrderSQL)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, productId);
                    insertStmt.setInt(3, quantity);
                    insertStmt.setString(4, "Processing");  // Default status
                    insertStmt.setString(5, trackingId);
                    insertStmt.setString(6, productDetails);

                    insertStmt.executeUpdate();

                    // Display tracking ID to the user
                    JOptionPane.showMessageDialog(this, "Order placed successfully. Tracking ID: " + trackingId);
                    loadOrdersData();  // Reload the table data
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to generate a unique tracking ID
    private String generateUniqueTrackingId() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789"; // Lowercase letters and numbers
        StringBuilder trackingId = new StringBuilder("TRACK");

        for (int i = 0; i < 6; i++) { // Adjust length as needed
            int randomIndex = (int) (Math.random() * characters.length());
            trackingId.append(characters.charAt(randomIndex));
        }

        return trackingId.toString();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OrderCreationPage().setVisible(true);
        });
    }
}

