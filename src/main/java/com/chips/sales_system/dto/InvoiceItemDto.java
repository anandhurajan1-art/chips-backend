package com.chips.sales_system.dto;

import java.math.BigDecimal;

public class InvoiceItemDto {
    private Long id;
    private Long invoiceItemId; // For returns
    private String itemName;
    private Double quantity;
    private String unit;
    private BigDecimal price;
    private BigDecimal amount;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvoiceItemId() { return invoiceItemId; }
    public void setInvoiceItemId(Long invoiceItemId) { this.invoiceItemId = invoiceItemId; }
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
