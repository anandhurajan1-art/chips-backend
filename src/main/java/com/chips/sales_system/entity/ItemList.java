package com.chips.sales_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class ItemList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemMaster item;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private Double quantity;
    private BigDecimal costPrice;
    private BigDecimal unitPrice;
    private BigDecimal mrpPrice;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ItemMaster getItem() { return item; }
    public void setItem(ItemMaster item) { this.item = item; }
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getMrpPrice() { return mrpPrice; }
    public void setMrpPrice(BigDecimal mrpPrice) { this.mrpPrice = mrpPrice; }
    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
}
