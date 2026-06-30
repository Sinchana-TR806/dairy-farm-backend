package com.sridairy.service;

import com.sridairy.model.Client;
import com.sridairy.model.MilkPurchase;
import com.sridairy.repository.ClientRepository;
import com.sridairy.repository.MilkPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MilkPurchaseService {

    @Autowired
    private MilkPurchaseRepository purchaseRepository;

    @Autowired
    private ClientRepository clientRepository;

    // CREATE PURCHASE
    public MilkPurchase createPurchase(MilkPurchase purchase) {

        Client client = purchase.getClient();

        double litres = purchase.getLitres();

        double goldEarned = 0;
        double silverEarned = 0;

        // Reward calculation
        if (litres >= 10) {
            goldEarned = litres * 2;
        } else {
            silverEarned = litres;
        }

        // Save purchase
        MilkPurchase saved = purchaseRepository.save(purchase);

        final double finalGoldEarned = goldEarned;
        final double finalSilverEarned = silverEarned;

        // Update client rewards
        if (client != null) {

            clientRepository.findById(client.getId()).ifPresent(c -> {

                double currentLitres =
                        c.getTotalLitres() != null
                                ? c.getTotalLitres()
                                : 0;

                double currentGold =
                        c.getGoldGrams() != null
                                ? c.getGoldGrams()
                                : 0;

                double currentSilver =
                        c.getSilverGrams() != null
                                ? c.getSilverGrams()
                                : 0;

                // Update totals
                c.setTotalLitres(currentLitres + saved.getLitres());

                c.setGoldGrams(currentGold + finalGoldEarned);

                c.setSilverGrams(currentSilver + finalSilverEarned);

                c.setProgress(
                        Math.min(100, c.getTotalLitres().intValue())
                );

                clientRepository.save(c);
            });
        }

        return saved;
    }

    // GET ALL PURCHASES
    public List<MilkPurchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    // GET PURCHASE BY ID
    public MilkPurchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    // DELETE PURCHASE
    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }

    // GET PURCHASES BY DATE
    public List<MilkPurchase> getPurchasesByDate(LocalDate date) {
        return purchaseRepository.findAll();
    }

    // GET PURCHASES BY CLIENT
    public List<MilkPurchase> getPurchasesByClient(Long clientId) {
        return purchaseRepository.findAll();
    }

    // TODAY SUMMARY
    public Map<String, Object> getTodaySummary() {

        Map<String, Object> summary = new HashMap<>();

        summary.put("date", LocalDate.now());
        summary.put("litres", 0);
        summary.put("revenue", 0);

        return summary;
    }
}
