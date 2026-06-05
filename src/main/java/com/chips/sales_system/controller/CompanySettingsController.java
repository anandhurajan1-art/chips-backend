package com.chips.sales_system.controller;

import com.chips.sales_system.entity.CompanySettings;
import com.chips.sales_system.repository.CompanySettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class CompanySettingsController {

    @Autowired
    private CompanySettingsRepository repository;

    @GetMapping
    public CompanySettings getSettings() {
        List<CompanySettings> allSettings = repository.findAll();
        if (allSettings.isEmpty()) {
            return new CompanySettings(); // Return empty object if nothing is set
        }
        return allSettings.get(0); // We only ever use the first row
    }

    @PostMapping
    public CompanySettings saveSettings(@RequestBody CompanySettings newSettings) {
        List<CompanySettings> allSettings = repository.findAll();
        CompanySettings settings;
        
        if (allSettings.isEmpty()) {
            settings = new CompanySettings();
        } else {
            settings = allSettings.get(0);
        }
        
        settings.setCompanyName(newSettings.getCompanyName());
        settings.setCompanyAddress(newSettings.getCompanyAddress());
        
        return repository.save(settings);
    }
}
