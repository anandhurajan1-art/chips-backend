package com.chips.sales_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "return_items")
public class ReturnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "return_id")
    private Returns returns;

    @ManyToOne
    @JoinColumn(name = "invoice_item_id")
    private InvoiceItem invoiceItem;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "returned_quantity")
    private Double returnedQuantity;

    @Column(name = "unit")
    private String unit;

    private BigDecimal price;

    private BigDecimal amount;

    private String reason;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Returns getReturns() { return returns; }
    public void setReturns(Returns returns) { this.returns = returns; }
    public InvoiceItem getInvoiceItem() { return invoiceItem; }
    public void setInvoiceItem(InvoiceItem invoiceItem) { this.invoiceItem = invoiceItem; }
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
