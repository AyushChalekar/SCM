package com.wms.gui;

import com.wms.services.StockService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockPage extends JPanel {

    private JComboBox<String> categoryComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private StockService stockService;

    public StockPage() {
        stockService = new StockService();

        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        String[] categories = {"All Categories", "Electronics", "Furniture", "Clothing"};
        categoryComboBox = new JComboBox<>(categories);

        searchPanel.add(new JLabel("Search by Category:"));
        searchPanel.add(categoryComboBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Content panel for stock
        JPanel stockContent = new JPanel();
        stockContent.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{"Product ID", "Name", "Category", "Stock"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        stockContent.add(scrollPane, BorderLayout.CENTER);
        add(stockContent, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryComboBox.getSelectedItem();
                String searchText = searchField.getText();
                JPanel searchResults = stockService.searchStock(category, searchText);
                remove(stockContent);
                add(searchResults, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
    }
}
