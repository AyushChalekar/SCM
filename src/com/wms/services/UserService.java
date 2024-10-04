package com.wms.services;

import com.wms.models.User;
import com.wms.models.UserData;
import com.wms.utils.DatabaseConnection;

import java.sql.*;

public class UserService {
    private Connection connection;

    public UserService() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if the count is greater than 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(String username, String email, String phone, String address, String firstname, String lastname) {
        String query = "UPDATE users SET email = ?, contact_no = ?, address = ?, firstname = ?, lastname = ? WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, firstname);
            ps.setString(5, lastname);

            ps.setString(6, username);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if something goes wrong
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
                String query = "INSERT INTO users (username, password, email, contact_no, address, role_id) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password); // Hash the password before saving it
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

    public boolean verifyPassword(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Compare hashed passwords (ensure you are using secure password handling)
                    return storedPassword.equals(password); // Use hashed comparison in a real implementation
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public UserData authenticateUser(String username, String password) {
        String query = "SELECT u.username, u.role_id, r.role_name, u.email, u.contact_no, u.address, u.firstname, u.lastname, e.employee_role " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.role_id " +
                "LEFT JOIN employees e ON u.e_id = e.e_id " + // Join with employees table to get employee role
                "WHERE u.username = ? AND u.password = ?"; // Ensure that password is hashed in real implementation

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password); // For security, consider using a hashed password here

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String user = rs.getString("username");
                    int roleId = rs.getInt("role_id");
                    String roleName = rs.getString("role_name");
                    String email = rs.getString("email");
                    String phone = rs.getString("contact_no");
                    String address = rs.getString("address");
                    String firstname = rs.getString("firstname"); // Retrieve firstname
                    String lastname = rs.getString("lastname");   // Retrieve lastname
                    String designation = rs.getString("employee_role"); // Retrieve designation

                    // Create a UserData instance with retrieved data
                    return new UserData(user, roleName, roleId, email, phone, address, firstname, lastname, designation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
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

    public void logUserLogin(String username, String ipAddress) {
        String query = "UPDATE users SET ip_address = ? WHERE username= ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, ipAddress);
            stmt.setString(2, username);
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
