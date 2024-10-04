package com.wms.ADMIN;

import com.wms.models.UserData;
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
    private UserData user; // Added UserData field

    public CategoriesPage(Connection conn, UserData userData) {
        this.connection = conn;
        this.user = userData; // Initialize UserData
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
        bottomPanel.setBackground(Color.WHITE); // Set background to white

        // Buttons
        JButton btnAddCategory = createStyledButton("Add Category", "icons/add.png");
        JButton btnBack = createStyledButton("Back", "icons/back.png");
        JButton btnLogout = createStyledButton("Logout", "icons/logout.png");

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
            new ProductPage(connection, user).setVisible(true); // Pass UserData to ProductPage
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
        button.setBackground(new Color(255, 110, 110)); // Orange color for buttons
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon(iconPath));

        // Adjust button size to ensure text fits
        button.setPreferredSize(new Dimension(200, 50)); // Increase button size as needed

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Change border color to black
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        )); // Rounded corners with shadow

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            button.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            e.printStackTrace();
        }
        // Button animation
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(255, 110, 110)); // Reset to orange
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

//            UserData userData = new UserData("john_doe", "Admin", 1, "email@example.com", "1234567890", "123 Main St", "First Name", "Last Name");            CategoriesPage categoriesPage = new CategoriesPage(connection, userData);

            // Add the CategoriesPage to the frame
           // frame.add(categoriesPage);

            // Set the frame visibility
            frame.setVisible(true);
        });
    }
}
