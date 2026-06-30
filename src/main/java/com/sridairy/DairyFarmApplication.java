package com.sridairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DairyFarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DairyFarmApplication.class, args);
        System.out.println("Sri Dairy Farm ERP Backend is running!");
    }
}