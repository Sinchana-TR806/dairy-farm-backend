package com.sridairy.repository;

import com.sridairy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Get all active products
    List<Product> findByStatus(String status);

    // Get products by category
    List<Product> findByCategory(String category);

    // Get low-stock products (stock less than their own alert threshold)
    @Query("SELECT p FROM Product p WHERE p.stockQty <= p.lowStockAlert")
    List<Product> findLowStockProducts();
}
