package com.chips.sales_system.dto;

import java.math.BigDecimal;

public class ReturnItemDto {
    private Long id;
    private Long invoiceItemId;
    private String itemName;
    private Double returnedQuantity;
    private String unit;
    private BigDecimal price;
    private BigDecimal amount;
    private String reason;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvoiceItemId() { return invoiceItemId; }
    public void setInvoiceItemId(Long invoiceItemId) { this.invoiceItemId = invoiceItemId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public Double getReturnedQuantity() { return returnedQuantity; }
    public void setReturnedQuantity(Double returnedQuantity) { this.returnedQuantity = returnedQuantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
