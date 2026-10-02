package com.chips.sales_system.controller;

import com.chips.sales_system.entity.Shop;
import com.chips.sales_system.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
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

    @PostMapping("/{id}/image")
    public org.springframework.http.ResponseEntity<?> uploadShopImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        try {
            Shop shop = shopRepository.findById(id).orElseThrow(() -> new RuntimeException("Shop not found"));
            String uploadDir = "uploads/shops/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            shop.setShopImageUrl("/uploads/shops/" + fileName);
            shopRepository.save(shop);
            return org.springframework.http.ResponseEntity.ok(shop);
        } catch (IOException e) {
            return org.springframework.http.ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
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
