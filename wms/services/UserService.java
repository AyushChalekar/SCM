package com.wms.services;

import com.wms.models.User;
import com.wms.utils.DatabaseConnection;

import java.sql.*;

public class UserService {
    private Connection connection;

    public UserService() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public boolean registerUser(String username, String password, String email, String phone, String address, String role) {
        try {
            // Fetch role_id from the roles table
            String roleQuery = "SELECT role_id FROM roles WHERE role_name = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleQuery);
            roleStatement.setString(1, role);
            ResultSet roleResultSet = roleStatement.executeQuery();

            if (roleResultSet.next()) {
                int roleId = roleResultSet.getInt("role_id");

                // Insert user data into the users table
                String query = "INSERT INTO users (username, password, email, phone, address, role_id) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);  // Hash the password before saving it
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, address);
                preparedStatement.setInt(6, roleId);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0; // Return true if a row was successfully inserted
            } else {
                return false; // Role not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(String username, String password, String role) {
        try {
            // Fetch role_id from the roles table
            String roleQuery = "SELECT role_id FROM roles WHERE role_name = ?";
            PreparedStatement roleStatement = connection.prepareStatement(roleQuery);
            roleStatement.setString(1, role);
            ResultSet roleResultSet = roleStatement.executeQuery();

            if (roleResultSet.next()) {
                int roleId = roleResultSet.getInt("role_id");

                // Insert user data into the users table
                String query = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setInt(3, roleId);

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } else {
                return false; // Role not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logUserLogin(int userId, String ipAddress) {
        String query = "UPDATE users SET last_login_ip = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, ipAddress);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int roleId = rs.getInt("role_id");

                    // Fetch role_name from the roles table
                    String roleQuery = "SELECT role_name FROM roles WHERE role_id = ?";
                    PreparedStatement roleStatement = connection.prepareStatement(roleQuery);
                    roleStatement.setInt(1, roleId);
                    ResultSet roleResultSet = roleStatement.executeQuery();

                    String roleName = null;
                    if (roleResultSet.next()) {
                        roleName = roleResultSet.getString("role_name");
                    }

                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            roleName
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
