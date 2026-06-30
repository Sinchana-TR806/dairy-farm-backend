package com.sridairy.controller;

import com.sridairy.model.Client;
import com.sridairy.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * STEP 6 — REST Controller (HTTP Layer)
 *
 * This class EXPOSES your backend as a web API.
 * Each method here = one URL endpoint that your frontend can call.
 *
 * @RestController  → This class handles HTTP requests and returns JSON automatically
 * @RequestMapping  → All URLs in this class start with /api/clients
 *
 * URL SUMMARY:
 * GET    /api/clients          → get all clients
 * GET    /api/clients/{id}     → get one client
 * POST   /api/clients          → create a new client
 * PUT    /api/clients/{id}     → update a client
 * DELETE /api/clients/{id}     → delete a client
 * GET    /api/clients/search?name=xxx → search by name
 * GET    /api/clients/stats    → dashboard counts
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // ── GET ALL CLIENTS ─────────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients")
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients); // returns HTTP 200 + JSON array
    }

    // ── GET ONE CLIENT BY ID ────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients/1")
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
            .map(client -> ResponseEntity.ok((Object) client))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Client not found with id: " + id));
    }

    // ── CREATE A NEW CLIENT ─────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients", { method: "POST", body: JSON... })
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client saved = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // HTTP 201
    }

    // ── UPDATE A CLIENT ─────────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients/1", { method: "PUT", body: JSON... })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client client) {
        try {
            Client updated = clientService.updateClient(id, client);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── DELETE A CLIENT ─────────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients/1", { method: "DELETE" })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok(Map.of("message", "Client deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── SEARCH BY NAME ──────────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients/search?name=kumar")
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(@RequestParam String name) {
        return ResponseEntity.ok(clientService.searchByName(name));
    }

    // ── FILTER BY SCHEME ────────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients/scheme/gold")
    @GetMapping("/scheme/{scheme}")
    public ResponseEntity<List<Client>> getByScheme(@PathVariable String scheme) {
        return ResponseEntity.ok(clientService.getByScheme(scheme));
    }

    // ── DASHBOARD STATS ─────────────────────────────────────────────────────────
    // Frontend calls: fetch("http://localhost:8080/api/clients/stats")
    // Returns: { total: 38, gold: 6, silver: 18, smsEnabled: 24 }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total",      clientService.getTotalClients());
        stats.put("gold",       clientService.getGoldClients());
        stats.put("silver",     clientService.getSilverClients());
        stats.put("smsEnabled", clientService.getSmsClients());
        return ResponseEntity.ok(stats);
    }
}
