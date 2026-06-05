package com.chips.sales_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReturnDto {
    private Long id;
    private String returnNo;
    private Long invoiceId;
    private String invoiceNo;
    private Long shopId;
    private String shopName;
    private Long staffId;
    private String staffName;
    private LocalDateTime returnDate;
    private BigDecimal totalReturnAmount;
    private List<ReturnItemDto> items;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public BigDecimal getTotalReturnAmount() { return totalReturnAmount; }
    public void setTotalReturnAmount(BigDecimal totalReturnAmount) { this.totalReturnAmount = totalReturnAmount; }
    public List<ReturnItemDto> getItems() { return items; }
    public void setItems(List<ReturnItemDto> items) { this.items = items; }
}
