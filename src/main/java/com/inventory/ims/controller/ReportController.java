package com.inventory.ims.controller;

import com.inventory.ims.dto.ApiResponse;
import com.inventory.ims.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboardStats() {
        Map<String, Object> stats = reportService.getDashboardStats();
        return ResponseEntity.ok(new ApiResponse(true, "Dashboard stats retrieved successfully", stats));
    }

    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse> getInventoryReport() {
        Map<String, Object> report = reportService.getInventoryReport();
        return ResponseEntity.ok(new ApiResponse(true, "Inventory report retrieved successfully", report));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> getOrderReport() {
        Map<String, Object> report = reportService.getOrderReport();
        return ResponseEntity.ok(new ApiResponse(true, "Order report retrieved successfully", report));
    }
}