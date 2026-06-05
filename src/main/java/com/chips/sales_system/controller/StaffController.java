package com.chips.sales_system.controller;

import com.chips.sales_system.dto.StaffDto;
import com.chips.sales_system.entity.Privilege;
import com.chips.sales_system.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    @PreAuthorize("hasAuthority('STAFF_MANAGEMENT') or hasAuthority('ADMIN')")
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STAFF_MANAGEMENT') or hasAuthority('ADMIN')")
    public ResponseEntity<StaffDto> createStaff(@RequestBody StaffDto staffDto) {
        return ResponseEntity.ok(staffService.createStaff(staffDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('STAFF_MANAGEMENT') or hasAuthority('ADMIN')")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable Long id, @RequestBody StaffDto staffDto) {
        return ResponseEntity.ok(staffService.updateStaff(id, staffDto));
    }

    @GetMapping("/privileges")
    @PreAuthorize("hasAuthority('STAFF_MANAGEMENT') or hasAuthority('ADMIN')")
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        return ResponseEntity.ok(staffService.getAllPrivileges());
    }
}
