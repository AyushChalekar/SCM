package com.wms.gui;

import com.wms.services.CategoryService;
import com.wms.models.Category;
import com.wms.utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;


public class CategoriesPage extends JPanel {

    private Connection connection;
    private JTable categoryTable;
    private CategoryService categoryService;

    public CategoriesPage(Connection conn) {
        this.connection = conn;
        setLayout(new BorderLayout());
        categoryService = new CategoryService();

        // Create Table Model
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Category Name"}, 0);
        categoryTable = new JTable(tableModel);

        // Increase table font size
        categoryTable.setFont(new Font("Arial", Font.PLAIN, 18));
        categoryTable.setRowHeight(30); // Adjust row height for larger text

        // Center table text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        categoryTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane tableScroll = new JScrollPane(categoryTable);

        // Add components
        add(tableScroll, BorderLayout.CENTER);

        // Bottom Panel with Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.BLACK); // Set background to match theme

        // Buttons
        JButton btnAddCategory = createStyledButton("Add Category", "path/to/add_icon.png");
        JButton btnBack = createStyledButton("Back", "path/to/back_icon.png");
        JButton btnLogout = createStyledButton("Logout", "path/to/logout_icon.png");

        bottomPanel.add(btnBack);
        bottomPanel.add(btnAddCategory);
        bottomPanel.add(btnLogout);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load categories into the table
        loadCategories();

        // Button actions
        btnAddCategory.addActionListener(e -> showAddCategoryDialog());

        // Back Button Action
        btnBack.addActionListener(e -> {
            Container parent = getParent();
            if (parent instanceof JFrame) {
                ((JFrame) parent).dispose(); // Close the current window
            }
            new ProductPage(conn).setVisible(true); // Show the login page
        });

        // Logout Button Action
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get parent frame and dispose of it
                Window parentWindow = SwingUtilities.getWindowAncestor(CategoriesPage.this);
                if (parentWindow instanceof JFrame) {
                    ((JFrame) parentWindow).dispose(); // Close current window
                }

                try {
                    new LoginFrame().setVisible(true); // Show the login page
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18)); // Large fonts for buttons
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        // Adjust button size to ensure text fits
        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        )); // Rounded corners with shadow

        // Button animation
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.GRAY);
            }
        });

        return button;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the frame for Categories Page
            JFrame frame = new JFrame("Categories Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Fullscreen mode
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame

            // Create the CategoriesPage with the database connection
            Connection connection = null; // Assuming this is how you get your connection
            try {
                connection = DatabaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            CategoriesPage categoriesPage = new CategoriesPage(connection);

            // Add the CategoriesPage to the frame
            frame.add(categoriesPage);

            // Set the frame visibility
            frame.setVisible(true);
        });
    }
}