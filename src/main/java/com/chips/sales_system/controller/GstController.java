package com.chips.sales_system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/gst")
public class GstController {

    @GetMapping("/{gstNumber}")
    public ResponseEntity<?> getGstDetails(@PathVariable String gstNumber) {
        // Here we could integrate with a real API like ClearTax or GSTZen.
        // For now, returning mock data as per implementation plan.
        Map<String, String> response = new HashMap<>();
        if (gstNumber == null || gstNumber.length() < 5) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid GST Number"));
        }
        
        response.put("gstNumber", gstNumber);
        response.put("details", "Mock Details for " + gstNumber + "\nLegal Name: MOCK BUSINESS ENTITY\nTrade Name: MOCK TRADE\nStatus: Active\nType: Regular");
        
        return ResponseEntity.ok(response);
    }
}
