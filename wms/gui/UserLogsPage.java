package com.wms.gui;

import com.wms.services.UserLogService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogsPage extends JPanel {

    private JComboBox<String> categoryComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private UserLogService userLogService;

    public UserLogsPage() {
        userLogService = new UserLogService();

        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        // Dummy categories for demonstration; you can replace with actual categories
        String[] categories = {"All Categories", "Electronics", "Furniture", "Clothing"};
        categoryComboBox = new JComboBox<>(categories);

        searchPanel.add(new JLabel("Search by Category:"));
        searchPanel.add(categoryComboBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Content panel for user logs
        JPanel userLogsContent = new JPanel();
        userLogsContent.add(new JLabel("User Logs Information Here"));
        add(userLogsContent, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryComboBox.getSelectedItem();
                String searchText = searchField.getText();
                JPanel searchResults = userLogService.searchUserLogs(category, searchText);
                remove(userLogsContent);
                add(searchResults, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }
}
