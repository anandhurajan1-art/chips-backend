package com.chips.sales_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SalesMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private LocalDate saleDate;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "salesMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesItems> salesItems = new ArrayList<>();

    public void addSalesItem(SalesItems item) {
        salesItems.add(item);
        item.setSalesMaster(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Shop getShop() { return shop; }
    public void setShop(Shop shop) { this.shop = shop; }
    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<SalesItems> getSalesItems() { return salesItems; }
    public void setSalesItems(List<SalesItems> salesItems) {
        this.salesItems.clear();
        if (salesItems != null) {
            salesItems.forEach(this::addSalesItem);
        }
    }
    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
}
