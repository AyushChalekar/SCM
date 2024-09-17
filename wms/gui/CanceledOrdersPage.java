package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class CanceledOrdersPage extends JPanel {
    public CanceledOrdersPage() {
        setLayout(new BorderLayout());

        // Example table for canceled orders
        String[] columns = { "Order ID", "Username", "Product", "Quantity", "Cancellation Date" };
        Object[][] data = {
                { "1", "john_doe", "Product A", "3", "2024-09-10" },
                { "2", "jane_smith", "Product B", "1", "2024-09-09" }
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back");
        add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "OrdersPage"));
    }
}
