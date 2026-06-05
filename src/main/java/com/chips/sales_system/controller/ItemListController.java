package com.chips.sales_system.controller;

import com.chips.sales_system.entity.ItemList;
import com.chips.sales_system.repository.ItemListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-list")
public class ItemListController {
    @Autowired
    private ItemListRepository itemListRepository;

    @GetMapping
    public List<ItemList> getAllItemLists(@RequestParam(required = false) Long branchId) {
        if (branchId != null) {
            return itemListRepository.findByBranchId(branchId);
        }
        return itemListRepository.findAll();
    }

    @PostMapping
    public ItemList createItemList(@RequestBody ItemList itemList) {
        return itemListRepository.save(itemList);
    }

    @PutMapping("/{id}")
    public ItemList updateItemList(@PathVariable Long id, @RequestBody ItemList itemList) {
        itemList.setId(id);
        return itemListRepository.save(itemList);
    }

    @DeleteMapping("/{id}")
    public void deleteItemList(@PathVariable Long id) {
        itemListRepository.deleteById(id);
    }
}
