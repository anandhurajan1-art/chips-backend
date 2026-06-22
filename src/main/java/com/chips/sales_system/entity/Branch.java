package com.chips.sales_system.entity;

import jakarta.persistence.*;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isGstBillEnabled;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Boolean getIsGstBillEnabled() { return isGstBillEnabled; }
    public void setIsGstBillEnabled(Boolean isGstBillEnabled) { this.isGstBillEnabled = isGstBillEnabled; }
}
