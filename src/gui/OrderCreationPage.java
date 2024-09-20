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

    private final JTextField userIdField;
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

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setForeground(Color.WHITE);
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Large font
        userIdField = new JTextField(10);
        userIdField.setFont(new Font("Arial", Font.BOLD, 24));  // Large font

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
        orderCreationPanel.add(userIdLabel, creationGbc);

        creationGbc.gridx = 1;
        orderCreationPanel.add(userIdField, creationGbc);

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
        gbc.weightx = 0.3;  // 1/3 of the page width
        getContentPane().add(orderCreationPanel, gbc);

        // Orders Table (largest table)
        String[] orderColumns = {"Order ID", "User ID", "Product ID", "Quantity", "Status"};
        ordersTableModel = new DefaultTableModel(orderColumns, 0);
        ordersTable = new JTable(ordersTableModel);
        styleTable(ordersTable);

        JScrollPane orderScrollPane = new JScrollPane(ordersTable);
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.7;  // 2/3 of the page width
        gbc.weighty = 1.0;
        getContentPane().add(orderScrollPane, gbc);

        // Category Table (small and below the order creation panel)

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

        button.setPreferredSize(new Dimension(150, 40));  // Smaller size for back and logout
        return button;
    }

    private void loadOrdersData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root");
             PreparedStatement stmt = conn.prepareStatement("SELECT order_id, user_id, product_id, quantity, status \n" +
                     "FROM wms1.orders\n" +
                     "WHERE status NOT IN ('Delivered', 'Canceled');\n");
             ResultSet rs = stmt.executeQuery()) {

            // Clear existing data
            ordersTableModel.setRowCount(0);

            // Add data to the table
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                String productid = rs.getString("product_id");
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");

                ordersTableModel.addRow(new Object[]{orderId, userId, productid, quantity, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void placeOrder() {
        // Get the input values from the form
        String userId = userIdField.getText();
        String productId = productIdField.getText();
        String productDetails = productDetailsField.getText();
        String quantity = quantityField.getText();

        // Generate a random Tracking ID
        String trackingId = generateTrackingID();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root");
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO orders (user_id, product_id, product_details, quantity, tracking_id, status) VALUES (?, ?, ?, ?, ?, ?)")) {

            // Set the values to insert into the database
            stmt.setInt(1, Integer.parseInt(userId));         // User ID
            stmt.setInt(2, Integer.parseInt(productId));      // Product ID
            stmt.setString(3, productDetails);               // Product Details
            stmt.setInt(4, Integer.parseInt(quantity));      // Quantity
            stmt.setString(5, trackingId);                   // Tracking ID
            stmt.setString(6, "In Progress");                // Status

            // Execute the SQL statement
            stmt.executeUpdate();

            // Notify the user that the order was placed successfully and display the tracking ID
            JOptionPane.showMessageDialog(this, "Order placed successfully! Tracking ID: " + trackingId);

            // Reload the orders table to include the new order
            loadOrdersData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to generate a random alphanumeric Tracking ID in the format "TRACKXXXXX"
    private String generateTrackingID() {
        int length = 5;  // Define the length of the random part (XXXXX)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  // Alphanumeric characters
        Random random = new Random();
        StringBuilder trackingId = new StringBuilder("TRACK");  // Start with "TRACK"

        for (int i = 0; i < length; i++) {
            trackingId.append(characters.charAt(random.nextInt(characters.length())));
        }

        return trackingId.toString();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderCreationPage orderCreationPage = new OrderCreationPage();
            orderCreationPage.setVisible(true);
        });
    }
}
