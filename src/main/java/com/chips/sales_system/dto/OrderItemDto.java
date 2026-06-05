package com.chips.sales_system.dto;

import java.math.BigDecimal;

public class OrderItemDto {
    private Long id;
    private Long itemListId;
    private String itemName;
    private Double quantity;
    private String unit;
    private BigDecimal price;
    private BigDecimal amount;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getItemListId() { return itemListId; }
    public void setItemListId(Long itemListId) { this.itemListId = itemListId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
