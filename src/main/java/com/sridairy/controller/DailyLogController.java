package com.sridairy.erp.controller;

import com.sridairy.erp.model.DailyLog;
import com.sridairy.erp.service.DailyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dailylogs")
@CrossOrigin("*")
public class DailyLogController {

    @Autowired
    private DailyLogService dailyLogService;

    @GetMapping
    public List<DailyLog> getAllLogs() {
        return dailyLogService.getAllLogs();
    }

    // ← NEW: this is what frontend calls on page load
    @GetMapping("/today")
    public List<DailyLog> getTodayLogs() {
        return dailyLogService.getLogsByDate(LocalDate.now());
    }
    @GetMapping("/grade-summary")
    public List<Object[]> getGradeSummary() {
        return dailyLogService.getGradeWiseTotals();
    }

    @PostMapping
    public DailyLog saveLog(@RequestBody DailyLog log) {
        log.setLogDate(LocalDate.now());  // ← always set date on save
        return dailyLogService.saveLog(log);
    }
}