package com.sridairy.controller;

import com.sridairy.model.Product;
import com.sridairy.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET ALL PRODUCTS
    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }

        return ResponseEntity.ok(product);
    }

    // CREATE PRODUCT
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Product updated) {

        Product existing = productRepository.findById(id).orElse(null);

        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }

        if (updated.getPrice() != null) {
            existing.setPrice(updated.getPrice());
        }

        if (updated.getStockQty() != null) {
            existing.setStockQty(updated.getStockQty());
        }

        if (updated.getUnit() != null) {
            existing.setUnit(updated.getUnit());
        }

        return ResponseEntity.ok(productRepository.save(existing));
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found");
        }

        productRepository.deleteById(id);

        return ResponseEntity.ok(
                Map.of("message", "Deleted successfully")
        );
    }

    // LOW STOCK PRODUCTS
    @GetMapping("/low-stockQty")
    public List<Product> getLowStockQty() {
        return productRepository.findAll();
    }
}
