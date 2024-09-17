package com.wms.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.wms.models.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.wms.utils.DatabaseConnection;

public class CategoryService {

    private Connection conn;

    public CategoryService() {
       // conn = DatabaseConnection.getConnection();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wms1", "root", "root");
            // Perform SQL operations
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle exception for closing connection
                }
            }
        }
    }
    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setInt(2, category.getCategoryId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");

                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (category_name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods for updating and deleting categories
}
