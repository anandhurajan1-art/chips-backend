package com.chips.sales_system.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
public class SalesItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id")
    @JsonIgnore
    private SalesMaster salesMaster;

    @ManyToOne
    @JoinColumn(name = "item_list_id")
    private ItemList itemList;

    private Double quantity;
    private BigDecimal amount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SalesMaster getSalesMaster() { return salesMaster; }
    public void setSalesMaster(SalesMaster salesMaster) { this.salesMaster = salesMaster; }
    public ItemList getItemList() { return itemList; }
    public void setItemList(ItemList itemList) { this.itemList = itemList; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
