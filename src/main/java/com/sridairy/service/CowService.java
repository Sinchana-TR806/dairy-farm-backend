package com.sridairy.erp.service;

import com.sridairy.erp.model.Cow;
import com.sridairy.erp.repository.CowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CowService {

    @Autowired
    private CowRepository cowRepository;

    public List<Cow> getAllCows() {
        return cowRepository.findAll();
    }

    public Optional<Cow> getCowById(Long id) {
        return cowRepository.findById(id);
    }

    public Cow saveCow(Cow cow) {
        return cowRepository.save(cow);
    }

    public Cow updateCow(Long id, Cow cow) {
        Cow existingCow = cowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cow not found"));

        existingCow.setCowId(cow.getCowId());
        existingCow.setName(cow.getName());
        existingCow.setBreed(cow.getBreed());
        existingCow.setAge(cow.getAge());
        existingCow.setGender(cow.getGender());
        existingCow.setStatus(cow.getStatus());

        return cowRepository.save(existingCow);
    }

    public void deleteCow(Long id) {
        cowRepository.deleteById(id);
    }
}
