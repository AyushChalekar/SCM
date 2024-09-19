package com.wms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import com.wms.utils.DatabaseConnection;
import com.wms.gui.ShipmentPage;
public class UpdateShipmentPage extends JFrame {
    public UpdateShipmentPage() {
        setTitle("Update Shipment Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {
            //new ShipmentPage().setVisible(true);
            try {
                Connection conn = DatabaseConnection.getConnection();
                new ShipmentPage(conn).setVisible(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(backButton, BorderLayout.NORTH);

        JLabel updateShipmentLabel = new JLabel("Update Shipment", SwingConstants.CENTER);
        updateShipmentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(updateShipmentLabel, BorderLayout.CENTER);

        // Add form for updating shipment
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("New Status:"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("Comments:"));
        formPanel.add(new JTextField());

        mainPanel.add(formPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateShipmentPage().setVisible(true));
    }
}
