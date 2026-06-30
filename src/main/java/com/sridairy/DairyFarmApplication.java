package com.sridairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DairyFarmApplication {

    public static void main(String[] args) {

        SpringApplication.run(DairyFarmApplication.class, args);

        try {
            // Wait for backend startup
            Thread.sleep(10000);

            // Open frontend in default browser
            Runtime.getRuntime().exec(
                    "cmd /c start C:\\Users\\User\\Downloads\\index_final.html"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Sri Dairy Farm ERP Backend is running!");
        System.out.println("API ready at: http://localhost:8080");
    }
}