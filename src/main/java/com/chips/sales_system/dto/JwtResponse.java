package com.chips.sales_system.dto;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String name;
    private String role;
    private List<String> privileges;

    public JwtResponse(String accessToken, Long id, String username, String name, String role, List<String> privileges) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
        this.privileges = privileges;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<String> getPrivileges() { return privileges; }
    public void setPrivileges(List<String> privileges) { this.privileges = privileges; }
}
