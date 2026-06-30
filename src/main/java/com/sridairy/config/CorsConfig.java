package com.sridairy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * STEP 2 — CORS (Cross-Origin Resource Sharing) Configuration
 *
 * WHY IS THIS NEEDED?
 * Your frontend (index.html) runs at one address, e.g. file:// or http://localhost:5500
 * Your backend runs at http://localhost:8080
 * Browsers BLOCK requests between different origins by default (security rule).
 * This class tells Spring Boot: "Allow requests from the frontend address."
 *
 * Without this file, every fetch() call from your HTML will fail with:
 * "Access to fetch at 'http://localhost:8080' has been blocked by CORS policy"
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    // Apply to ALL API endpoints (/** means every path)
                    .addMapping("/**")
                    // Allow requests from these frontend origins
                    // Add your actual frontend URL here
                    .allowedOrigins(
                        "http://localhost:5500",      // Live Server in VS Code
                        "http://127.0.0.1:5500",      // Live Server alternative
                        "http://localhost:3000",       // React dev server
                        "http://localhost:63342",      // IntelliJ built-in server
                        "null"                         // Opening HTML file directly (file://)
                    )
                    // Allow these HTTP methods
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    // Allow all headers
                    .allowedHeaders("*");
            }
        };
    }
}
