package com.inventory.ims.repository;

import com.inventory.ims.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findBySku(String sku);
    boolean existsBySku(String sku);
    List<Product> findByCategory(String category);
    List<Product> findByIsActiveTrue();
    
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.minStockLevel")
    List<Product> findLowStockProducts();
}