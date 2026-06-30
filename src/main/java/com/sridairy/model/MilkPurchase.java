package com.sridairy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

/**
 * Represents ONE milk purchase entry (from the "Daily Purchase" page in your frontend).
 *
 * Each row = one purchase entry saved in the database.
 * @ManyToOne → many purchases can belong to one client
 * @JoinColumn → links to the client using client_id foreign key
 */
@Entity
@Table(name = "milk_purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilkPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to which client this purchase belongs to
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private LocalDate purchaseDate;  // Date of collection (e.g. 2025-06-05)

    private Double litres;           // How many litres purchased

    private String grade;            // "A+", "A", "B", "C", "D"

    private Double pricePerLitre;    // Rate (₹ per litre)

    private Double totalAmount;      // litres × pricePerLitre

    private Double goldEarned;       // Gold grams earned from this purchase

    private Double silverEarned;     // Silver grams earned from this purchase

    private String notes;            // Optional notes
    public java.util.Map<String, Object> getTodaySummary() {

        java.util.Map<String, Object> summary = new java.util.HashMap<>();

        summary.put("date", java.time.LocalDate.now());
        summary.put("litres", 0);
        summary.put("revenue", 0);

        return summary;
    }
}
