package com.sridairy.repository;

import com.sridairy.model.MilkPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository for MilkPurchase — handles all database operations for milk purchases.
 */
@Repository
public interface MilkPurchaseRepository extends JpaRepository<MilkPurchase, Long> {

    // Get all purchases for a specific client
    List<MilkPurchase> findByClientId(Long clientId);

    // Get all purchases on a specific date
    List<MilkPurchase> findByPurchaseDate(LocalDate date);

    // Get all purchases between two dates (for date range reports)
    List<MilkPurchase> findByPurchaseDateBetween(LocalDate start, LocalDate end);

    // Get purchases for a client between two dates
    List<MilkPurchase> findByClientIdAndPurchaseDateBetween(Long clientId, LocalDate start, LocalDate end);

    // Custom query: total litres collected today
    @Query("SELECT SUM(m.litres) FROM MilkPurchase m WHERE m.purchaseDate = :date")
    Double totalLitresByDate(LocalDate date);

    // Custom query: total revenue today
    @Query("SELECT SUM(m.totalAmount) FROM MilkPurchase m WHERE m.purchaseDate = :date")
    Double totalRevenueByDate(LocalDate date);
}
