package com.chips.sales_system.controller;

import com.chips.sales_system.entity.Unit;
import com.chips.sales_system.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    @Autowired
    private UnitRepository unitRepository;

    @GetMapping
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    @PostMapping
    public Unit createUnit(@RequestBody Unit unit) {
        return unitRepository.save(unit);
    }

    @PutMapping("/{id}")
    public Unit updateUnit(@PathVariable Long id, @RequestBody Unit unit) {
        unit.setId(id);
        return unitRepository.save(unit);
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable Long id) {
        unitRepository.deleteById(id);
    }
}
