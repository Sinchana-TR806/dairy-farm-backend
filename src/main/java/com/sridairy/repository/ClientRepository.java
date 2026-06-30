package com.sridairy.repository;

import com.sridairy.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Find by scheme
    List<Client> findByScheme(String scheme);
    Client findByName(String name);

    // Find by status
    List<Client> findByStatus(String status);

    // Find SMS enabled clients
    List<Client> findBySmsEnabled(Boolean smsEnabled);

    // Search by name
    List<Client> findByNameContainingIgnoreCase(String name);

    // Find exact client name


    // Counts
    long countByScheme(String scheme);


    long countBySmsEnabled(Boolean smsEnabled);
}