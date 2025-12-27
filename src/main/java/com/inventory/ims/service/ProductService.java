package com.inventory.ims.service;

import com.inventory.ims.dto.ProductDTO;
import com.inventory.ims.entity.Product;
import com.inventory.ims.exception.ResourceNotFoundException;
import com.inventory.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable("products")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productRepository.existsBySku(productDTO.getSku())) {
            throw new RuntimeException("Product with SKU already exists: " + productDTO.getSku());
        }
        
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setMinStockLevel(productDTO.getMinStockLevel());
        product.setCategory(productDTO.getCategory());
        product.setSupplier(productDTO.getSupplier());
        product.setIsActive(productDTO.getIsActive());
        
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<ProductDTO> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSku(product.getSku());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setMinStockLevel(product.getMinStockLevel());
        dto.setCategory(product.getCategory());
        dto.setSupplier(product.getSupplier());
        dto.setIsActive(product.getIsActive());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setMinStockLevel(dto.getMinStockLevel());
        product.setCategory(dto.getCategory());
        product.setSupplier(dto.getSupplier());
        product.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return product;
    }
}