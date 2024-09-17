package com.wms.gui;

import com.wms.models.Product;
import com.wms.services.OrderService;
import com.wms.services.ProductService;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OrderCreationPage extends JFrame {

    private ProductService productService;
    private OrderService orderService;

    public OrderCreationPage() {
        // conn = DatabaseConnection.getConnection();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root");
            // Perform SQL operations
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle exception for closing connection
                }
            }
        }

        this.productService = new ProductService(conn);
        orderService = new OrderService(conn);

        setTitle("Create Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JPanel productListPanel = new JPanel(new BorderLayout());
        JTable productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        productListPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(productListPanel, BorderLayout.CENTER);

        JPanel orderDetailsPanel = new JPanel(new GridLayout(5, 2));
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        JButton placeOrderButton = new JButton("Place Order");

        orderDetailsPanel.add(customerIdLabel);
        orderDetailsPanel.add(customerIdField);
        orderDetailsPanel.add(quantityLabel);
        orderDetailsPanel.add(quantityField);
        orderDetailsPanel.add(new JLabel()); // empty cell
        orderDetailsPanel.add(placeOrderButton);

        mainPanel.add(orderDetailsPanel, BorderLayout.EAST);

        // Load products
        try {
            List<Product> products = productService.getAllProducts();
            String[] columnNames = {"Product ID", "Name", "Quantity", "Price"};
            Object[][] data = new Object[products.size()][4];

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                data[i][0] = product.getId();
                data[i][1] = product.getName(); // Corrected method
                data[i][2] = product.getQuantity();
                data[i][3] = product.getPrice();
            }

            productTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // Place order button action
        placeOrderButton.addActionListener(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                int selectedProductRow = productTable.getSelectedRow();
                int productId = (int) productTable.getValueAt(selectedProductRow, 0);
                int quantity = Integer.parseInt(quantityField.getText());

                orderService.placeOrder(customerId, productId, quantity); // Corrected method
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new OrderCreationPage().setVisible(true);
    }
}
