package com.wms.services;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportService {

    private Connection connection;

    public ReportService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DefaultCategoryDataset getSalesDataForBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String query = "SELECT category, SUM(total_sales) as sales FROM sales GROUP BY category";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String category = rs.getString("category");
                double sales = rs.getDouble("sales");
                dataset.addValue(sales, "Sales", category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public DefaultPieDataset getCategoryDataForPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        String query = "SELECT category, COUNT(*) as count FROM products GROUP BY category";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                dataset.setValue(category, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }
}
