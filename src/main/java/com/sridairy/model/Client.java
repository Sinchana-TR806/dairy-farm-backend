package com.sridairy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * STEP 3 — Model (Entity) Class
 *
 * This class represents ONE CLIENT in your system.
 * Each field = one column in the database table.
 *
 * @Entity  → tells JPA: "create a database table for this class"
 * @Table   → names the table "clients"
 * @Data    → Lombok auto-generates getters, setters, toString (no need to write them)
 * @Id     → marks which field is the primary key
 * @GeneratedValue → auto-increments the ID (1, 2, 3...)
 */
@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // Auto ID: 1, 2, 3...

    @Column(nullable = false)
    private String name;          // Client's full name

    private String phone;         // Phone number (for SMS)

    private String scheme;        // "gold", "silver", or "none"

    private Double totalLitres = 0.0;

    private Double goldGrams = 0.0;

    private Double silverGrams = 0.0;

    private Integer progress = 0;

    private Boolean smsEnabled;   // Whether to send SMS notifications

    private String address;       // Client's address

    @Column(nullable = false)
    private String status = "active"; // "active" or "inactive"
}
