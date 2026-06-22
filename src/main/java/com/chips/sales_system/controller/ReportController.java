package com.chips.sales_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/item-count")
    public ResponseEntity<?> getItemCountReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long shopId,
            @RequestParam(required = false) String itemName) {

        StringBuilder sql = new StringBuilder("SELECT i.itemName, SUM(i.quantity) FROM SalesOrderItem i WHERE 1=1 ");
        
        if (startDate != null && !startDate.isEmpty()) sql.append(" AND CAST(i.order.orderDate AS date) >= CAST(:startDate AS date)");
        if (endDate != null && !endDate.isEmpty()) sql.append(" AND CAST(i.order.orderDate AS date) <= CAST(:endDate AS date)");
        if (shopId != null) sql.append(" AND i.order.shop.id = :shopId");
        if (itemName != null && !itemName.isEmpty()) sql.append(" AND i.itemName = :itemName");
        
        sql.append(" GROUP BY i.itemName");

        Query query = entityManager.createQuery(sql.toString());
        
        if (startDate != null && !startDate.isEmpty()) query.setParameter("startDate", java.time.LocalDate.parse(startDate));
        if (endDate != null && !endDate.isEmpty()) query.setParameter("endDate", java.time.LocalDate.parse(endDate));
        if (shopId != null) query.setParameter("shopId", shopId);
        if (itemName != null && !itemName.isEmpty()) query.setParameter("itemName", itemName);

        List<Object[]> results = query.getResultList();
        List<Map<String, Object>> response = results.stream().map(r -> {
            Map<String, Object> map = new HashMap<>();
            map.put("itemName", r[0]);
            map.put("totalQuantity", r[1]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profit")
    public ResponseEntity<?> getProfitReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long shopId) {

        StringBuilder sql = new StringBuilder(
            "SELECT i.itemName, SUM(i.quantity), SUM(i.quantity * i.price), SUM(i.quantity * i.costPrice), SUM(i.quantity * (i.price - i.costPrice)) " +
            "FROM SalesOrderItem i WHERE 1=1 "
        );
        
        if (startDate != null && !startDate.isEmpty()) sql.append(" AND CAST(i.order.orderDate AS date) >= CAST(:startDate AS date)");
        if (endDate != null && !endDate.isEmpty()) sql.append(" AND CAST(i.order.orderDate AS date) <= CAST(:endDate AS date)");
        if (shopId != null) sql.append(" AND i.order.shop.id = :shopId");
        
        sql.append(" GROUP BY i.itemName");

        Query query = entityManager.createQuery(sql.toString());
        
        if (startDate != null && !startDate.isEmpty()) query.setParameter("startDate", java.time.LocalDate.parse(startDate));
        if (endDate != null && !endDate.isEmpty()) query.setParameter("endDate", java.time.LocalDate.parse(endDate));
        if (shopId != null) query.setParameter("shopId", shopId);

        List<Object[]> results = query.getResultList();
        List<Map<String, Object>> response = results.stream().map(r -> {
            Map<String, Object> map = new HashMap<>();
            map.put("itemName", r[0]);
            map.put("totalQuantity", r[1]);
            map.put("totalSales", r[2]);
            map.put("totalCost", r[3]);
            map.put("profit", r[4]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
