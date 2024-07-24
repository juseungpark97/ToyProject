package com.kh.toy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.toy.model.Benefits;
import com.kh.toy.repository.BenefitsRepository;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private BenefitsRepository benefitsRepository;

    @GetMapping
    public ResponseEntity<List<Benefits>> getAllServices() {
        List<Benefits> services = benefitsRepository.findAll();
        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
    }
}