package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class SearchProductPage extends JPanel {
    public SearchProductPage() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));

        formPanel.add(new JLabel("Product Name:"));
        JTextField txtProductName = new JTextField();
        formPanel.add(txtProductName);

        formPanel.add(new JLabel("Category:"));
        JTextField txtCategory = new JTextField();
        formPanel.add(txtCategory);

        formPanel.add(new JLabel("Min Price:"));
        JTextField txtMinPrice = new JTextField();
        formPanel.add(txtMinPrice);

        formPanel.add(new JLabel("Max Price:"));
        JTextField txtMaxPrice = new JTextField();
        formPanel.add(txtMaxPrice);

        JButton btnSearch = new JButton("Search");
        JButton btnBack = new JButton("Back");

        formPanel.add(btnSearch);
        formPanel.add(btnBack);

        add(formPanel, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> handleSearch(txtProductName.getText(), txtCategory.getText(), txtMinPrice.getText(), txtMaxPrice.getText()));
        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "ProductsPage"));
    }

    private void handleSearch(String name, String category, String minPrice, String maxPrice) {
        // Implement product search logic
        JOptionPane.showMessageDialog(this, "Search Results for: " + name);
    }
}
