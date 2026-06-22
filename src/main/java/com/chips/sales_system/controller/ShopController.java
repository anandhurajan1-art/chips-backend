package com.chips.sales_system.controller;

import com.chips.sales_system.entity.Shop;
import com.chips.sales_system.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping
    public List<Shop> getAllShops(@RequestParam(required = false) Long branchId) {
        if (branchId != null) {
            return shopRepository.findByBranchId(branchId);
        }
        return shopRepository.findAll();
    }

    @PostMapping
    public Shop createShop(@RequestBody Shop shop) {
        return shopRepository.save(shop);
    }

    @PutMapping("/{id}")
    public Shop updateShop(@PathVariable Long id, @RequestBody Shop shop) {
        shop.setId(id);
        return shopRepository.save(shop);
    }

    @Autowired
    private com.chips.sales_system.repository.SalesOrderItemRepository salesOrderItemRepository;

    @Autowired
    private com.chips.sales_system.repository.ReturnItemRepository returnItemRepository;

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
    }

    @GetMapping("/{id}/most-ordered-items")
    public org.springframework.http.ResponseEntity<?> getMostOrderedItems(@PathVariable Long id) {
        List<Object[]> results = salesOrderItemRepository.findMostOrderedItemsByShopId(id);
        List<java.util.Map<String, Object>> response = results.stream().map(r -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("itemName", r[0]);
            map.put("totalQuantity", r[1]);
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return org.springframework.http.ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/most-returned-items")
    public org.springframework.http.ResponseEntity<?> getMostReturnedItems(@PathVariable Long id) {
        List<Object[]> results = returnItemRepository.findMostReturnedItemsByShopId(id);
        List<java.util.Map<String, Object>> response = results.stream().map(r -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("itemName", r[0]);
            map.put("totalQuantity", r[1]);
            return map;
        }).collect(java.util.stream.Collectors.toList());
        return org.springframework.http.ResponseEntity.ok(response);
    }
}
