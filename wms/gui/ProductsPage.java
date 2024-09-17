package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ProductsPage extends JFrame {
    private Connection connection;

    public ProductsPage(Connection connection) {
       this.connection = connection;
    //public ProductsPage() {

    initComponents();
    }

    private void initComponents() {
        setTitle("Products Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1)); // Example Grid Layout

        // Create and add components (buttons, tables, etc.)
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton viewButton = new JButton("View Products");

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);

        // Add action listeners, etc.

        setVisible(true);
    }
}
