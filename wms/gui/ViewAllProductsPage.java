package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class ViewAllProductsPage extends JPanel {
    public ViewAllProductsPage() {
        setLayout(new BorderLayout());

        // Placeholder for displaying all products
        JTextArea productListArea = new JTextArea("All products will be displayed here.");
        productListArea.setEditable(false);

        add(new JScrollPane(productListArea), BorderLayout.CENTER);
    }
}