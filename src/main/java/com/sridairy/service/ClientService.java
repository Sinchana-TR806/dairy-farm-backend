package com.sridairy.service;

import com.sridairy.model.Client;
import com.sridairy.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * STEP 5 — Service Class (Business Logic Layer)
 *
 * The Service sits between the Controller and the Repository.
 * Controller  →  Service  →  Repository  →  Database
 *
 * Why have a Service layer?
 * - Keep business logic (calculations, rules) separate from HTTP handling
 * - E.g. "When milk reaches 300L, add 1g gold" — that rule goes here, not in Controller
 * - Makes code easier to test and maintain
 *
 * @Service → tells Spring: "register this as a service bean (singleton)"
 * @Autowired → Spring automatically injects the ClientRepository instance
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // ── GET ALL CLIENTS ─────────────────────────────────────────────────────────
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // ── GET ONE CLIENT BY ID ────────────────────────────────────────────────────
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // ── CREATE A NEW CLIENT ─────────────────────────────────────────────────────
    public Client createClient(Client client) {
        // Set defaults if not provided
        if (client.getStatus() == null) client.setStatus("active");
        if (client.getTotalLitres() == null) client.setTotalLitres(0.0);
        if (client.getGoldGrams() == null)
            client.setGoldGrams(0.0);

        if (client.getSilverGrams() == null)
            client.setSilverGrams(0.0);

        if (client.getProgress() == null)
            client.setProgress(0);
        if (client.getSmsEnabled() == null) client.setSmsEnabled(true);
        if (client.getScheme() == null) client.setScheme("none");

        return clientRepository.save(client);
    }

    // ── UPDATE AN EXISTING CLIENT ───────────────────────────────────────────────
    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(existingClient -> {
            // Only update fields that are provided (not null)
            if (updatedClient.getName() != null)
                existingClient.setName(updatedClient.getName());
            if (updatedClient.getPhone() != null)
                existingClient.setPhone(updatedClient.getPhone());
            if (updatedClient.getScheme() != null)
                existingClient.setScheme(updatedClient.getScheme());
            if (updatedClient.getSmsEnabled() != null)
                existingClient.setSmsEnabled(updatedClient.getSmsEnabled());
            if (updatedClient.getAddress() != null)
                existingClient.setAddress(updatedClient.getAddress());
            if (updatedClient.getStatus() != null)
                existingClient.setStatus(updatedClient.getStatus());

            return clientRepository.save(existingClient);
        }).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    // ── DELETE A CLIENT ─────────────────────────────────────────────────────────
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    // ── SEARCH CLIENTS BY NAME ──────────────────────────────────────────────────
    public List<Client> searchByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    // ── FILTER BY SCHEME ────────────────────────────────────────────────────────
    public List<Client> getByScheme(String scheme) {
        return clientRepository.findByScheme(scheme);
    }

    // ── DASHBOARD STATS ─────────────────────────────────────────────────────────
    public long getTotalClients() { return clientRepository.count(); }
    public long getGoldClients()  { return clientRepository.countByScheme("gold"); }
    public long getSilverClients(){ return clientRepository.countByScheme("silver"); }
    public long getSmsClients()   { return clientRepository.countBySmsEnabled(true); }
}
