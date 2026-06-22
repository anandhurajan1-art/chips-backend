package com.chips.sales_system.controller;

import com.chips.sales_system.entity.Branch;
import com.chips.sales_system.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {
    
    @Autowired
    private BranchRepository branchRepository;

    @GetMapping
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @PostMapping
    public Branch createBranch(@RequestBody Branch branch) {
        return branchRepository.save(branch);
    }

    @PutMapping("/{id}")
    public Branch updateBranch(@PathVariable Long id, @RequestBody Branch updatedBranch) {
        return branchRepository.findById(id).map(branch -> {
            branch.setName(updatedBranch.getName());
            branch.setIsGstBillEnabled(updatedBranch.getIsGstBillEnabled());
            return branchRepository.save(branch);
        }).orElseThrow(() -> new RuntimeException("Branch not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteBranch(@PathVariable Long id) {
        branchRepository.deleteById(id);
    }
}
