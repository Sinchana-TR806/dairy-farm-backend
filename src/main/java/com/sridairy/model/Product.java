package com.sridairy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a product in the dairy farm's catalogue.
 * Examples: Desi Ghee, Butter, Paneer, Full Cream Milk, etc.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;          // "Desi Ghee", "Butter", etc.

    private String unit;          // "kg", "litre", "piece"

    private Double price;         // Price per unit in ₹

    @Column(name = "stock")
    private Double stockQty;      // Current stock quantity

    private Double lowStockAlert; // Show warning when stock falls below this

    private String category;      // "ghee", "milk", "cream", etc.

    private String icon;          // Emoji icon like 🧈

    private String status;        // "active" or "inactive"
}
