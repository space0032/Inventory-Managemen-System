package com.inventory.ims.controller;

import com.inventory.ims.dto.ApiResponse;
import com.inventory.ims.entity.Order;
import com.inventory.ims.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved successfully", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Order retrieved successfully", order));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        Order order = orderService.createOrder(userId);
        return ResponseEntity.ok(new ApiResponse(true, "Order created successfully", order));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(new ApiResponse(true, "Order status updated successfully", order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(new ApiResponse(true, "User orders retrieved successfully", orders));
    }
}