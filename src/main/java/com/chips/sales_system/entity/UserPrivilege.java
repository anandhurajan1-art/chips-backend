package com.chips.sales_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_privileges")
public class UserPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Privilege getPrivilege() { return privilege; }
    public void setPrivilege(Privilege privilege) { this.privilege = privilege; }
}
