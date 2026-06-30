package com.sridairy.erp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Integer quantity;

    private Double price;
}
