package com.sridairy.erp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;@Entity
@Data
@Table(name = "daily_log")
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "grade")
    private String grade;

    @Column(name = "product_key")
    private String productKey;

    @Column(name = "quantity_display")
    private String quantityDisplay;

    private Double quantity;

    private String unit;

    private Double amount;

    private String scheme;

    @Column(name = "reward_amount")
    private Double rewardAmt;

    @Column(name = "reward_display")
    private String rewardDisplay;

    @Column(name = "reward_type")
    private String rewardType;

    @Column(name = "log_time")
    private String logTime;

    @Column(name = "log_date")
    private LocalDate logDate;
}