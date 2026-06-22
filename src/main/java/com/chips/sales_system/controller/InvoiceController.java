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
    public ResponseEntity<List<InvoiceDto>> getAllInvoices(
            @RequestParam(required = false) Long shopId,
            @RequestParam(required = false) String date) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(shopId, date));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GENERATE_BILL') or hasAuthority('ADMIN') or hasAuthority('VIEW_BILL')")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PostMapping("/generate/{orderId}")
    @PreAuthorize("hasAuthority('GENERATE_BILL') or hasAuthority('ADMIN')")
    public ResponseEntity<?> generateInvoice(@PathVariable Long orderId, Authentication authentication) {
        String username = authentication.getName();
        try {
            return ResponseEntity.ok(invoiceService.generateInvoice(orderId, username));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("message", e.getMessage() != null ? e.getMessage() : e.toString()));
        }
    }
}
