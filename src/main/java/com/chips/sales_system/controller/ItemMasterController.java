package com.chips.sales_system.controller;

import com.chips.sales_system.entity.ItemMaster;
import com.chips.sales_system.repository.ItemMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemMasterController {
    @Autowired
    private ItemMasterRepository itemMasterRepository;

    @GetMapping
    public List<ItemMaster> getAllItems(@RequestParam(required = false) Long branchId) {
        if (branchId != null) {
            return itemMasterRepository.findByBranchId(branchId);
        }
        return itemMasterRepository.findAll();
    }

    @PostMapping
    public org.springframework.http.ResponseEntity<?> createItem(@RequestBody ItemMaster item) {
        if (item.getBranch() != null && item.getItemName() != null) {
            List<ItemMaster> existing = itemMasterRepository.findByBranchId(item.getBranch().getId());
            boolean exists = existing.stream().anyMatch(i -> i.getItemName().equalsIgnoreCase(item.getItemName()));
            if (exists) {
                return org.springframework.http.ResponseEntity.badRequest().body("Item with this name already exists in the selected branch");
            }
        }
        return org.springframework.http.ResponseEntity.ok(itemMasterRepository.save(item));
    }

    @PutMapping("/{id}")
    public ItemMaster updateItem(@PathVariable Long id, @RequestBody ItemMaster item) {
        item.setId(id);
        return itemMasterRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemMasterRepository.deleteById(id);
    }
}
