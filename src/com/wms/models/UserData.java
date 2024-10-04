package com.wms.models;

public class UserData {
    private String username;
    private String roleName;
    private int roleId;
    private String email;
    private String phone;
    private String address;
    private String firstname;  // Added firstname
    private String lastname;   // Added lastname
    private String designation; // Add this field if not already present

    public UserData(String username, String roleName, int roleId, String email, String phone, String address, String firstname, String lastname,String designation) {
        this.username = username;
        this.roleName = roleName;
        this.roleId = roleId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.firstname = firstname;  // Initialize firstname
        this.lastname = lastname;
        this.designation = designation; // Initialize designation
// Initialize lastname
    }

    // Getters
    public String getUsername() { return username; }
    public String getRoleName() { return roleName; }
    public int getRoleId() { return roleId; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getFirstname() { return firstname; }  // Getter for firstname
    public String getLastname() { return lastname; }    // Getter for lastname
    public String getDesignation() {
        return designation;
    }
    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setFirstname(String firstname) { this.firstname = firstname; }  // Setter for firstname
    public void setLastname(String lastname) { this.lastname = lastname; }    // Setter for lastname



    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(roleName);
    }

    public boolean isDelivery() {
        return "Delivery Partner".equalsIgnoreCase(roleName);
    }

    public boolean isCustomer() {
        return "Customer".equalsIgnoreCase(roleName);
    }

    public boolean isSupplier() {
        return "Supplier".equalsIgnoreCase(roleName);
    }

    public boolean isEmployee() {
        return "Employee".equalsIgnoreCase(roleName);
    }
}
