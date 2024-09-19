package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class AllProductsPage extends JPanel {
    public AllProductsPage() {
        setLayout(new BorderLayout());

        // Example table for products
        String[] columns = { "Product ID", "Name", "Category", "Quantity", "Price", "Description" };
        Object[][] data = {
                { "1", "Product A", "Category 1", "100", "10.99", "Description A" },
                { "2", "Product B", "Category 2", "200", "15.49", "Description B" }
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back");
        add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "ProductsPage"));
    }
}
