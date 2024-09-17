package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class IncomingShipmentsPage extends JPanel {
    public IncomingShipmentsPage() {
        setLayout(new BorderLayout());

        // Example table for incoming shipments
        String[] columns = { "Shipment ID", "Supplier", "Product", "Quantity", "Status" };
        Object[][] data = {
                { "1", "Supplier A", "Product A", "50", "In Transit" },
                { "2", "Supplier B", "Product B", "30", "Arrived" }
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back");
        add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "ShipmentsPage"));
    }
}
