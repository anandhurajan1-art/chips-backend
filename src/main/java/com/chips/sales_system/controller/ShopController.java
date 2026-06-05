package com.chips.sales_system.controller;

import com.chips.sales_system.entity.Shop;
import com.chips.sales_system.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable Long id) {
        shopRepository.deleteById(id);
    }
}
