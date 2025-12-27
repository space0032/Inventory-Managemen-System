package com.inventory.ims.service;

import com.inventory.ims.entity.Order;
import com.inventory.ims.entity.Product;
import com.inventory.ims.repository.OrderRepository;
import com.inventory.ims.repository.ProductRepository;
import com.inventory.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalProducts", productRepository.count());
        stats.put("totalUsers", userRepository.count());
        stats.put("totalOrders", orderRepository.count());
        stats.put("lowStockProducts", productRepository.findLowStockProducts().size());
        stats.put("pendingOrders", orderRepository.findByStatus(Order.OrderStatus.PENDING).size());
        
        return stats;
    }

    public Map<String, Object> getInventoryReport() {
        Map<String, Object> report = new HashMap<>();
        
        List<Product> allProducts = productRepository.findAll();
        List<Product> lowStockProducts = productRepository.findLowStockProducts();
        
        BigDecimal totalInventoryValue = allProducts.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        report.put("totalProducts", allProducts.size());
        report.put("lowStockProducts", lowStockProducts);
        report.put("totalInventoryValue", totalInventoryValue);
        report.put("activeProducts", productRepository.findByIsActiveTrue().size());
        
        return report;
    }

    public Map<String, Object> getOrderReport() {
        Map<String, Object> report = new HashMap<>();
        
        List<Order> allOrders = orderRepository.findAll();
        
        report.put("totalOrders", allOrders.size());
        report.put("pendingOrders", orderRepository.findByStatus(Order.OrderStatus.PENDING).size());
        report.put("confirmedOrders", orderRepository.findByStatus(Order.OrderStatus.CONFIRMED).size());
        report.put("deliveredOrders", orderRepository.findByStatus(Order.OrderStatus.DELIVERED).size());
        
        return report;
    }
}