package com.chips.sales_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDto {
    private Long id;
    private String invoiceNo;
    private Long orderId;
    private String orderNo;
    private Long shopId;
    private String shopName;
    private String shopPlace;
    private String shopPhoneNumber;
    private Long staffId;
    private String staffName;
    private LocalDateTime invoiceDate;
    private BigDecimal totalAmount;
    private BigDecimal grandTotal;
    private List<InvoiceItemDto> items;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public String getShopPlace() { return shopPlace; }
    public void setShopPlace(String shopPlace) { this.shopPlace = shopPlace; }
    public String getShopPhoneNumber() { return shopPhoneNumber; }
    public void setShopPhoneNumber(String shopPhoneNumber) { this.shopPhoneNumber = shopPhoneNumber; }
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public LocalDateTime getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDateTime invoiceDate) { this.invoiceDate = invoiceDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public BigDecimal getGrandTotal() { return grandTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public List<InvoiceItemDto> getItems() { return items; }
    public void setItems(List<InvoiceItemDto> items) { this.items = items; }
}
