package com.inventory.ims.service;

import com.inventory.ims.entity.Order;
import com.inventory.ims.entity.Product;
import com.inventory.ims.entity.User;
import com.inventory.ims.exception.ResourceNotFoundException;
import com.inventory.ims.repository.OrderRepository;
import com.inventory.ims.repository.ProductRepository;
import com.inventory.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO);

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}