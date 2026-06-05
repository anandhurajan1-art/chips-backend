package com.chips.sales_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "privilege_name")
    private String privilegeName;

    @Column(name = "privilege_key", unique = true)
    private String privilegeKey;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPrivilegeName() { return privilegeName; }
    public void setPrivilegeName(String privilegeName) { this.privilegeName = privilegeName; }

    public String getPrivilegeKey() { return privilegeKey; }
    public void setPrivilegeKey(String privilegeKey) { this.privilegeKey = privilegeKey; }
}
