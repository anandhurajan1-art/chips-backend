package com.chips.sales_system.controller;

import com.chips.sales_system.dto.InvoiceDto;
import com.chips.sales_system.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    @PreAuthorize("hasAuthority('GENERATE_BILL') or hasAuthority('ADMIN') or hasAuthority('VIEW_BILL')")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GENERATE_BILL') or hasAuthority('ADMIN') or hasAuthority('VIEW_BILL')")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PostMapping("/generate/{orderId}")
    @PreAuthorize("hasAuthority('GENERATE_BILL') or hasAuthority('ADMIN')")
    public ResponseEntity<InvoiceDto> generateInvoice(@PathVariable Long orderId, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(invoiceService.generateInvoice(orderId, username));
    }
}
