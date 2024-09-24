package com.wms.gui;




import javax.swing.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.*;
import java.util.Random;
public class OrderCreationPage extends JFrame {
    private final JTable ordersTable;
    private final DefaultTableModel ordersTableModel;

    private final JTextField usernameField;  // Changed from userIdField to usernameField
    private final JTextField productIdField;
    private final JTextField productDetailsField;
    private final JTextField quantityField;
    private final JTextField categoryField;
    private final JButton placeOrderButton;
    private final JButton backButton;
    private final JButton logoutButton;

    public OrderCreationPage() {
        setTitle("Create Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Order Creation Panel (on the right side)
        JPanel orderCreationPanel = new JPanel(new GridBagLayout());
        orderCreationPanel.setBackground(Color.BLACK);
        orderCreationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");  // Updated label
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Arial", Font.BOLD, 24));  // Large font

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
        categoryField = new JTextField(10);
        categoryField.setFont(new Font("Arial", Font.BOLD, 24));  // Large font

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setForeground(Color.WHITE);
        productIdLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
        productIdField = new JTextField(10);
        productIdField.setFont(new Font("Arial", Font.BOLD, 24));  // Large font

        JLabel productDetailsLabel = new JLabel("Product Details:");
        productDetailsLabel.setForeground(Color.WHITE);
        productDetailsLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
        productDetailsField = new JTextField(10);
        productDetailsField.setFont(new Font("Arial", Font.BOLD, 24));  // Large font

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
        quantityField = new JTextField(10);
        quantityField.setFont(new Font("Arial", Font.BOLD, 24));  // Large font

        placeOrderButton = createStyledButton("Place Order", "icons/place_order.png");
        placeOrderButton.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
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
        orderCreationPanel.add(categoryLabel, creationGbc);

        creationGbc.gridx = 1;
        orderCreationPanel.add(categoryField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 2;
        orderCreationPanel.add(productIdLabel, creationGbc);

        creationGbc.gridx = 1;
        orderCreationPanel.add(productIdField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 3;
        orderCreationPanel.add(productDetailsLabel, creationGbc);

        creationGbc.gridx = 1;
        orderCreationPanel.add(productDetailsField, creationGbc);

        creationGbc.gridx = 0;
        creationGbc.gridy = 4;
        orderCreationPanel.add(quantityLabel, creationGbc);

        creationGbc.gridx = 1;
        orderCreationPanel.add(quantityField, creationGbc);

        creationGbc.gridx = 1;
        creationGbc.gridy = 5;
        orderCreationPanel.add(placeOrderButton, creationGbc);

        gbc.gridx = 1;  // Placing on the right side
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.2;  // Adjusting to make it smaller
        getContentPane().add(orderCreationPanel, gbc);

        // Orders Table (largest table)
        String[] orderColumns = {"Order ID", "Username", "Product ID", "Quantity", "Status", "Tracking ID", "Order Details"};
        ordersTableModel = new DefaultTableModel(orderColumns, 0);
        ordersTable = new JTable(ordersTableModel);
        styleTable(ordersTable);

        JScrollPane orderScrollPane = new JScrollPane(ordersTable);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.8;  // Making this larger to fill remaining space
        gbc.weighty = 1.0;
        getContentPane().add(orderScrollPane, gbc);

        // Back Button (bottom left)
        backButton = createStyledButton("Back", "icons/back.png");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        getContentPane().add(backButton, gbc);
        backButton.addActionListener(e -> dispose());

        // Logout Button (bottom right)
        logoutButton = createStyledButton("Logout", "icons/logout.png");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        getContentPane().add(logoutButton, gbc);
        logoutButton.addActionListener(e -> {
            dispose();
            try {
                new LoginFrame().setVisible(true);  // Return to login page
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Load initial data
        loadOrdersData();
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
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        button.setPreferredSize(new Dimension(100, 30));  // Smaller size for back and logout
        return button;
    }

    private void loadOrdersData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root");
             PreparedStatement stmt = conn.prepareStatement("SELECT o.order_id, u.username, o.product_id, o.quantity, o.status, o.tracking_id, o.order_deatils " +
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
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");
                String trackingId = rs.getString("tracking_id");
                String order_deatils = rs.getString("order_deatils");
                ordersTableModel.addRow(new Object[]{orderId, username, productId, quantity, status, trackingId, order_deatils});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void placeOrder() {
        String username = usernameField.getText();
        String category = categoryField.getText();
        String productIdStr = productIdField.getText();
        String quantityStr = quantityField.getText();
        String productDetails = productDetailsField.getText();

        if (username.isEmpty() || category.isEmpty() || productIdStr.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root")) {
            // Retrieve user_id based on username
            PreparedStatement userStmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            userStmt.setString(1, username);
            ResultSet userResult = userStmt.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("user_id");
                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);

                // Insert order into the database
                String insertOrderSQL = "INSERT INTO orders (user_id, product_id, quantity, status, tracking_id, order_deatils) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertOrderSQL)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, productId);
                    insertStmt.setInt(3, quantity);
                    insertStmt.setString(4, "Processing");  // Default status
                    insertStmt.setString(5, "TRACK" + System.currentTimeMillis());  // Generate a tracking ID
                    insertStmt.setString(6, productDetails);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Order placed successfully.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OrderCreationPage().setVisible(true);
        });
    }
}

