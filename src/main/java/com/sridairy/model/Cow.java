package com.sridairy.erp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cows")
@Data
public class Cow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cowId;
    private String name;
    private String breed;
    private Integer age;
    private String gender;
    private String status;
}
