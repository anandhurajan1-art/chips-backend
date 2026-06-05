package com.chips.sales_system.dto;

import java.util.List;

public class StaffDto {
    private Long id;
    private String name;
    private String phone;
    private String username;
    private String password;
    private String role;
    private String status;
    private List<String> privileges;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getPrivileges() { return privileges; }
    public void setPrivileges(List<String> privileges) { this.privileges = privileges; }
}
