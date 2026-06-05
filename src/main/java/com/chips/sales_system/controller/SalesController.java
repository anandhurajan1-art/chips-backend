package com.chips.sales_system.controller;

import com.chips.sales_system.entity.SalesMaster;
import com.chips.sales_system.repository.SalesMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    @Autowired
    private SalesMasterRepository salesMasterRepository;

    @PostMapping
    public SalesMaster createSale(@RequestBody SalesMaster salesMaster) {
        // Relationship is established in SalesMaster setSalesItems method automatically
        // However, we ensure it's set just in case
        if (salesMaster.getSalesItems() != null) {
            salesMaster.getSalesItems().forEach(item -> item.setSalesMaster(salesMaster));
        }
        return salesMasterRepository.save(salesMaster);
    }

    @GetMapping("/reports")
    public List<SalesMaster> getSalesReports(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) Long shopId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        if (branchId != null) {
            if (shopId != null) {
                return salesMasterRepository.findByBranchIdAndShopIdAndSaleDateBetween(branchId, shopId, startDate, endDate);
            } else {
                return salesMasterRepository.findByBranchIdAndSaleDateBetween(branchId, startDate, endDate);
            }
        } else {
            if (shopId != null) {
                return salesMasterRepository.findByShopIdAndSaleDateBetween(shopId, startDate, endDate);
            } else {
                return salesMasterRepository.findBySaleDateBetween(startDate, endDate);
            }
        }
    }
    @PutMapping("/{id}")
    public SalesMaster updateSale(@PathVariable Long id, @RequestBody SalesMaster updatedSalesMaster) {
        return salesMasterRepository.findById(id).map(existingSalesMaster -> {
            existingSalesMaster.setShop(updatedSalesMaster.getShop());
            existingSalesMaster.setSaleDate(updatedSalesMaster.getSaleDate());
            existingSalesMaster.setTotalAmount(updatedSalesMaster.getTotalAmount());
            
            // The setSalesItems method in SalesMaster clears old items and adds new ones
            existingSalesMaster.setSalesItems(updatedSalesMaster.getSalesItems());
            
            return salesMasterRepository.save(existingSalesMaster);
        }).orElseThrow(() -> new RuntimeException("Sale not found with id " + id));
    }
}
