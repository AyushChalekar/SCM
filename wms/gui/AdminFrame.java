/*package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import com.wms.utils.DatabaseConnection;
import com.wms.gui.LoginFrame;
public class AdminFrame extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField searchField;
    private JButton[] navButtons;

    public AdminFrame() {
        setTitle("Admin Dashboard - Warehouse Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main panel with CardLayout to switch between different pages
        mainPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();

        // Left panel with navigation buttons
        JPanel leftPanel = new JPanel(new GridLayout(10, 1, 10, 10));
        leftPanel.setBackground(Color.DARK_GRAY);

        // Create navigation buttons
        navButtons = new JButton[9];
        navButtons[0] = createNavButton("Products");
        navButtons[1] = createNavButton("Categories");
        navButtons[2] = createNavButton("Current Stock");
        navButtons[3] = createNavButton("Customers");
        navButtons[4] = createNavButton("Suppliers");
        navButtons[5] = createNavButton("Sales");
        navButtons[6] = createNavButton("Order Creation");
        navButtons[7] = createNavButton("Order Tracking");
        navButtons[8] = createNavButton("User Logs");

        // Logout button
        JButton logoutButton = createNavButton("Logout");

        // Add buttons to the left panel
        for (JButton button : navButtons) {
            leftPanel.add(button);
        }
        leftPanel.add(logoutButton);

        // Top panel with search box
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search Product by Category: ");
        searchLabel.setForeground(Color.WHITE);
        searchField = new JTextField(30);
        JButton searchButton = new JButton("Search");

        topPanel.setBackground(Color.DARK_GRAY);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Adding panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Load pages into the main panel
        loadPages();

        // Button actions
        for (int i = 0; i < navButtons.length; i++) {
            final int index = i;
            navButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    highlightSelectedButton(index);
                    switch (index) {
                        case 0:
                            cardLayout.show(mainPanel, "Products");
                            break;
                        case 1:
                            cardLayout.show(mainPanel, "Categories");
                            break;
                        case 2:
                            cardLayout.show(mainPanel, "CurrentStock");
                            break;
                        case 3:
                            cardLayout.show(mainPanel, "Customers");
                            break;
                        case 4:
                            cardLayout.show(mainPanel, "Suppliers");
                            break;
                        case 5:
                            cardLayout.show(mainPanel, "Sales");
                            break;
                        case 6:
                            cardLayout.show(mainPanel, "OrderCreation");
                            break;
                        case 7:
                            cardLayout.show(mainPanel, "OrderTracking");
                            break;
                        case 8:
                            cardLayout.show(mainPanel, "UserLogs");
                            break;
                    }
                }
            });
        }

        // Logout button action
        logoutButton.addActionListener(e -> {
            //new LoginFrame().setVisible(true);
            try {
                Connection conn = DatabaseConnection.getConnection();
                new LoginFrame(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setFocusPainted(false);
        return button;
    }

    private void loadPages() {
       // mainPanel.add(new ProductsPanel(), "Products");
       /* mainPanel.add(new CategoriesPanel(), "Categories");
        mainPanel.add(new CurrentStockPanel(), "CurrentStock");
        mainPanel.add(new CustomersPanel(), "Customers");
        mainPanel.add(new SuppliersPanel(), "Suppliers");
        mainPanel.add(new SalesPanel(), "Sales");
        mainPanel.add(new OrderCreationPanel(), "OrderCreation");
        mainPanel.add(new OrderTrackingPanel(), "OrderTracking");
        mainPanel.add(new UserLogsPanel(), "UserLogs"); //
    }

    private void highlightSelectedButton(int index) {
        // Reset colors of all buttons
        for (JButton button : navButtons) {
            button.setBackground(Color.GRAY);
        }
        // Highlight the selected button
        navButtons[index].setBackground(Color.ORANGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminFrame().setVisible(true);
        });
    }
}
*/