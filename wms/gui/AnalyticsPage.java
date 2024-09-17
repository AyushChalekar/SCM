package com.wms.gui;

import javax.swing.*;
import java.awt.*;

public class AnalyticsPage extends JPanel {
    public AnalyticsPage() {
        setLayout(new BorderLayout());

        // Example for analytics (graph and statistics)
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, 1));
        chartPanel.add(new JLabel("Analytics will be displayed here", SwingConstants.CENTER));

        // Add example graphs and statistics
        // Example: Use libraries like JFreeChart to add actual charts

        JButton btnBack = new JButton("Back");
        add(chartPanel, BorderLayout.CENTER);
        add(btnBack, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> ((CardLayout) getParent().getLayout()).show(getParent(), "AdminHomePage"));
    }
}
