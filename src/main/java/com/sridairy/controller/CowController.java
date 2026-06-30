package com.sridairy.erp.controller;

import com.sridairy.erp.model.Cow;
import com.sridairy.erp.service.CowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cows")
@CrossOrigin(origins = "https://dairyfarm-management.netlify.app")
public class CowController {

    @Autowired
    private CowService cowService;

    @GetMapping
    public List<Cow> getAllCows() {
        return cowService.getAllCows();
    }

    @GetMapping("/{id}")
    public Cow getCowById(@PathVariable Long id) {
        return cowService.getCowById(id)
                .orElseThrow(() -> new RuntimeException("Cow not found"));
    }

    @PostMapping
    public Cow addCow(@RequestBody Cow cow) {
        return cowService.saveCow(cow);
    }

    @PutMapping("/{id}")
    public Cow updateCow(@PathVariable Long id,
                         @RequestBody Cow cow) {
        return cowService.updateCow(id, cow);
    }

    @DeleteMapping("/{id}")
    public String deleteCow(@PathVariable Long id) {
        cowService.deleteCow(id);
        return "Cow deleted successfully";
    }
}
