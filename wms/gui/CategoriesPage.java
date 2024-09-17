package com.wms.gui;

import com.wms.services.CategoryService;
import com.wms.models.Category;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriesPage extends JPanel {

    private JTable categoryTable;
    private CategoryService categoryService;

    public CategoriesPage() {
        setLayout(new BorderLayout());
        categoryService = new CategoryService();

        // Create Table Model
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Category Name"}, 0);
        categoryTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(categoryTable);

        // Add components
        add(tableScroll, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAddCategory = new JButton("Add Category");
        JButton btnBack = new JButton("Back");

        rightPanel.add(btnAddCategory);
        rightPanel.add(btnBack);

        add(rightPanel, BorderLayout.EAST);

        // Load categories into table
        loadCategories();

        // Button actions
        btnAddCategory.addActionListener(e -> showAddCategoryDialog());
        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "home"));
    }

    private void loadCategories() {
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.setRowCount(0); // Clear existing rows

        List<Category> categories = categoryService.getCategories();
        for (Category category : categories) {
            model.addRow(new Object[]{category.getCategoryId(), category.getCategoryName()});
        }
    }

    private void showAddCategoryDialog() {
        // Implement the dialog for adding a category
        // Sample dialog code (you need to implement the actual functionality)
        JDialog dialog = new JDialog();
        dialog.setTitle("Add Category");
        dialog.setSize(300, 150);
        dialog.setLayout(new GridLayout(2, 2));

        JTextField nameField = new JTextField();

        dialog.add(new JLabel("Category Name:"));
        dialog.add(nameField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Category category = new Category(0, nameField.getText());
            categoryService.addCategory(category);
            loadCategories();
            dialog.dispose();
        });

        dialog.add(addButton);
        dialog.setVisible(true);
    }
}
