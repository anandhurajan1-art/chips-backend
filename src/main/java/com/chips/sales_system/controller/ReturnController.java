package com.chips.sales_system.controller;

import com.chips.sales_system.dto.ReturnDto;
import com.chips.sales_system.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_RETURN_REPORT') or hasAuthority('ADMIN')")
    public ResponseEntity<List<ReturnDto>> getAllReturns() {
        return ResponseEntity.ok(returnService.getAllReturns());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('RETURN_ENTRY') or hasAuthority('ADMIN')")
    public ResponseEntity<ReturnDto> createReturn(@RequestBody ReturnDto returnDto, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(returnService.createReturn(returnDto, username));
    }
}
