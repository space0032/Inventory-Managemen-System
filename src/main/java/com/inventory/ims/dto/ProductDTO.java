package com.inventory.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    @NotBlank(message = "SKU is required")
    private String sku;
    
    private String description;
    
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive")
    private BigDecimal price;
    
    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive")
    private Integer quantity;
    
    @PositiveOrZero(message = "Min stock level must be positive")
    private Integer minStockLevel;
    
    private String category;
    private String supplier;
    private Boolean isActive;
}