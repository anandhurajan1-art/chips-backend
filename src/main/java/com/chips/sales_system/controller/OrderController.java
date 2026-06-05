package com.chips.sales_system.controller;

import com.chips.sales_system.dto.OrderDto;
import com.chips.sales_system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ORDERS') or hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VIEW_ORDERS') or hasAuthority('ADMIN')")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TAKE_ORDER') or hasAuthority('ADMIN')")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(orderService.createOrder(orderDto, username));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('TAKE_ORDER') or hasAuthority('ADMIN')")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('TAKE_ORDER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Order cancelled successfully");
    }
}
