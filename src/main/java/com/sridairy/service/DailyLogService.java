package com.sridairy.erp.service;

import com.sridairy.erp.model.DailyLog;
import com.sridairy.erp.repository.DailyLogRepository;
import com.sridairy.model.Client;
import com.sridairy.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyLogService {

    @Autowired
    private DailyLogRepository dailyLogRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<DailyLog> getAllLogs() {
        return dailyLogRepository.findAll();
    }

    public List<DailyLog> getLogsByDate(LocalDate date) {
        return dailyLogRepository.findByLogDate(date);
    }

    public DailyLog saveLog(DailyLog log) {

        DailyLog saved = dailyLogRepository.save(log);

        System.out.println("LOG CLIENT NAME = " + log.getClientName());

        Client client = clientRepository.findByName(log.getClientName());

        System.out.println("FOUND CLIENT = " + client);

        if (client != null) {

            double qty = log.getQuantity() != null
                    ? log.getQuantity()
                    : 0.0;

            double totalLitres = client.getTotalLitres() != null
                    ? client.getTotalLitres()
                    : 0.0;

            double goldGrams = client.getGoldGrams() != null
                    ? client.getGoldGrams()
                    : 0.0;

            double silverGrams = client.getSilverGrams() != null
                    ? client.getSilverGrams()
                    : 0.0;

            client.setTotalLitres(totalLitres + qty);

            if ("gold".equalsIgnoreCase(client.getScheme())) {
                client.setGoldGrams(goldGrams + (qty * 0.01));
            } else if ("silver".equalsIgnoreCase(client.getScheme())) {
                client.setSilverGrams(silverGrams + (qty * 0.05));
            }

            client.setProgress(
                    Math.min(100, client.getTotalLitres().intValue())
            );

            clientRepository.save(client);

            System.out.println("CLIENT UPDATED SUCCESSFULLY");

        } else {

            System.out.println("CLIENT NOT FOUND");
        }

        return saved;
    }
    public List<Object[]> getGradeWiseTotals() {
        return dailyLogRepository.getGradeWiseTotals();
    }
}
