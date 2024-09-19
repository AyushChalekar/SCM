package com.wms.gui;

import com.wms.gui.LoginFrame;
import com.wms.gui.OrderManagementFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateOrderFrame extends JFrame {

    public CreateOrderFrame(String username) {
        setTitle("Create Order");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBackground(new Color(40, 40, 40));

        JLabel productLabel = new JLabel("Product Details:");
        productLabel.setForeground(Color.WHITE);
        JTextArea productArea = new JTextArea(5, 20);
        JScrollPane productScroll = new JScrollPane(productArea);

        JLabel orderStatusLabel = new JLabel("Order Status:");
        orderStatusLabel.setForeground(Color.WHITE);
        JTextField statusField = new JTextField();

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.setBackground(new Color(70, 70, 70));
        createOrderButton.setForeground(Color.WHITE);

        mainPanel.add(productLabel);
        mainPanel.add(productScroll);
        mainPanel.add(orderStatusLabel);
        mainPanel.add(statusField);
        mainPanel.add(createOrderButton);

        JButton logoutButton = new JButton("Logout");
        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(bottomPanel);

        add(mainPanel);

        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productDetails = productArea.getText();
                String orderStatus = statusField.getText();

                // Add logic to create order in the database
                createOrder(username, productDetails, orderStatus);
            }
        });

        logoutButton.addActionListener(e -> {
            dispose();
            try {
                new LoginFrame();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new OrderManagementFrame(username);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createOrder(String username, String productDetails, String orderStatus) {
        // Add database interaction code here to create an order
        // Example SQL command: INSERT INTO orders (user_id, product_details, order_status) VALUES (?, ?, ?);
    }
}
