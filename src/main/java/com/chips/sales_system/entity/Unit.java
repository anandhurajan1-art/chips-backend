package com.chips.sales_system.entity;

import jakarta.persistence.*;

@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }
}
