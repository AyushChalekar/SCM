// AddProductPage.java
package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class AddProductPage extends JPanel {
    public AddProductPage() {
        setLayout(new GridLayout(6, 2));

        JLabel lblProductName = new JLabel("Product Name:");
        JTextField txtProductName = new JTextField();
        JLabel lblCategory = new JLabel("Category:");
        JTextField txtCategory = new JTextField();
        JLabel lblQuantity = new JLabel("Quantity:");
        JTextField txtQuantity = new JTextField();
        JLabel lblPrice = new JLabel("Price:");
        JTextField txtPrice = new JTextField();
        JLabel lblDescription = new JLabel("Description:");
        JTextArea txtDescription = new JTextArea();
        JButton btnSubmit = new JButton("Add Product");
        JButton btnBack = new JButton("Back");

        add(lblProductName);
        add(txtProductName);
        add(lblCategory);
        add(txtCategory);
        add(lblQuantity);
        add(txtQuantity);
        add(lblPrice);
        add(txtPrice);
        add(lblDescription);
        add(new JScrollPane(txtDescription));
        add(btnSubmit);
        add(btnBack);

        btnSubmit.addActionListener(e -> {
            // Handle adding the product to the database
        });

        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "ProductsPage"));
    }
}
