package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class AddCategoryPage extends JPanel {
    public AddCategoryPage() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2));

        formPanel.add(new JLabel("Category Name:"));
        JTextField txtCategoryName = new JTextField();
        formPanel.add(txtCategoryName);

        JButton btnAdd = new JButton("Add");
        JButton btnBack = new JButton("Back");

        formPanel.add(btnAdd);
        formPanel.add(btnBack);

        add(formPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> handleAddCategory(txtCategoryName.getText()));
        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "ProductsPage"));
    }

    private void handleAddCategory(String categoryName) {
        // Implement category addition logic
        JOptionPane.showMessageDialog(this, "Category Added: " + categoryName);
    }
}
