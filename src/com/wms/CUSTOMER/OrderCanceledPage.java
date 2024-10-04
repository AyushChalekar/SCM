package com.wms.CUSTOMER;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.List;
import com.wms.ADMIN.LoginFrame;
import com.wms.models.UserData;

public class OrderCanceledPage extends JFrame {

    private JTextField searchField;
    private JComboBox<String> sortOptions;
    private JButton backButton, logoutButton, saveButton, searchButton, sortbutton;
    private JTable ordersTable;
    private DefaultTableModel ordersTableModel;

    public OrderCanceledPage(UserData currentUser) {
        setTitle("Create Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Changed to white background
        getContentPane().setLayout(new BorderLayout());

        // Top Panel for Search and Sort
        JPanel searchSortPanel = new JPanel(new GridBagLayout());
        searchSortPanel.setBackground(Color.WHITE); // Changed to white background
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
        buttonPanel.setBackground(Color.WHITE); // Changed to white background
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

        buttonPanel.add(backButton);

        buttonPanel.add(logoutButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadOrdersData(currentUser);
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
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 110, 110)); // Changed to orange color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        return button;
    }
    private void loadOrdersData(UserData currentUser) {
        String query = "SELECT o.order_id, u.username, o.product_id, o.product_details, o.quantity, o.status, o.tracking_id, o.order_deatils " +
                "FROM orders o JOIN users u ON o.user_id = u.user_id " +
                "WHERE u.username = ? AND o.status like 'Cancel%'";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.200:3306/wms1", "LAP", "root");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the current user's ID in the query
            stmt.setString(1, currentUser.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                // Clear existing data
                ordersTableModel.setRowCount(0);

                // Add data to the table
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    String username = rs.getString("username");
                    int productId = rs.getInt("product_id");
                    String productDetails = rs.getString("product_details");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    String trackingId = rs.getString("tracking_id");
                    String orderDetails = rs.getString("order_deatils");

                    // Add row to the table model
                    ordersTableModel.addRow(new Object[]{orderId, username, productId, productDetails, quantity, status, trackingId, orderDetails});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Assuming you have a valid UserData object for the current user
//            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name");            new OrderCanceledPage(userData).setVisible(true);
        });
    }
}
