package com.wms.services;

import com.wms.models.Product;
import com.wms.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProductService {
    private Connection conn;

    public ProductService(Connection conn) {
        this.conn = conn;
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),           // Adjust column names here
                        rs.getString("product_name"),
                        rs.getString("product_category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
                products.add(product);
            }
        }
        return products;
    }
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
                products.add(product);
            }
        }
        return products;
    }
}
