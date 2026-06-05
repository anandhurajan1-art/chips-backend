package com.chips.sales_system.controller;

import com.chips.sales_system.entity.ItemMaster;
import com.chips.sales_system.repository.ItemMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    public ItemMaster createItem(@RequestBody ItemMaster item) {
        return itemMasterRepository.save(item);
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
