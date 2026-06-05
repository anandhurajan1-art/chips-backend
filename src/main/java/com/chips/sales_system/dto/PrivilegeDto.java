package com.chips.sales_system.dto;

public class PrivilegeDto {
    private Long id;
    private String privilegeName;
    private String privilegeKey;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPrivilegeName() { return privilegeName; }
    public void setPrivilegeName(String privilegeName) { this.privilegeName = privilegeName; }
    public String getPrivilegeKey() { return privilegeKey; }
    public void setPrivilegeKey(String privilegeKey) { this.privilegeKey = privilegeKey; }
}
