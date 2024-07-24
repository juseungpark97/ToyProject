package com.kh.toy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.toy.model.Benefits;
import com.kh.toy.repository.BenefitsRepository;

@Service
public class BenefitsService {

    @Autowired
    private BenefitsRepository benefitsRepository;

    public List<Benefits> getAllBenefits() {
        return benefitsRepository.findAll();
    }

    public Benefits getBenefitById(String benefitId) {
        return benefitsRepository.findById(benefitId).orElse(null);
    }

    public Benefits saveBenefit(Benefits benefits) {
        return benefitsRepository.save(benefits);
    }
}
