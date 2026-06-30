package com.sridairy.controller;

import com.sridairy.model.MilkPurchase;
import com.sridairy.service.MilkPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Milk Purchases (Daily Purchase page in your frontend).
 *
 * URL SUMMARY:
 * GET  /api/purchases              → all purchases
 * POST /api/purchases              → save a new purchase (auto-calculates rewards)
 * GET  /api/purchases/today        → today's purchases
 * GET  /api/purchases/date?d=yyyy-MM-dd → purchases on a specific date
 * GET  /api/purchases/client/{id}  → all purchases for one client
 * GET  /api/purchases/summary      → today's total litres + revenue (for dashboard)
 */
@RestController
@RequestMapping("/api/purchases")
public class MilkPurchaseController {

    @Autowired
    private MilkPurchaseService purchaseService;

    // ── GET ALL PURCHASES ────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<MilkPurchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    // ── CREATE A NEW PURCHASE ────────────────────────────────────────────────────
    // The service will auto-calculate rewards and update the client's balance
    @PostMapping
    public ResponseEntity<MilkPurchase> create(@RequestBody MilkPurchase purchase) {
        MilkPurchase saved = purchaseService.createPurchase(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ── GET TODAY'S PURCHASES ────────────────────────────────────────────────────
    @GetMapping("/today")
    public ResponseEntity<List<MilkPurchase>> getToday() {
        return ResponseEntity.ok(purchaseService.getPurchasesByDate(LocalDate.now()));
    }

    // ── GET BY SPECIFIC DATE ─────────────────────────────────────────────────────
    // Example: GET /api/purchases/date?d=2025-06-05
    @GetMapping("/date")
    public ResponseEntity<List<MilkPurchase>> getByDate(
            @RequestParam("d") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(purchaseService.getPurchasesByDate(date));
    }

    // ── GET BY CLIENT ────────────────────────────────────────────────────────────
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<MilkPurchase>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(purchaseService.getPurchasesByClient(clientId));
    }

    // ── DASHBOARD SUMMARY ────────────────────────────────────────────────────────
    // Returns: { date: "2025-06-05", litres: 1240.0, revenue: 48320.0 }
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getTodaySummary() {
        return ResponseEntity.ok(purchaseService.getTodaySummary());
    }
}
