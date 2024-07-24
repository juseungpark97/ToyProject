package com.kh.toy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.toy.model.Benefits;
import com.kh.toy.service.ApiService;
	
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/fetchServiceList")
    public ResponseEntity<String> fetchServiceList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int perPage) {
        List<Benefits> savedBenefits = apiService.fetchAndSaveServiceList(page, perPage);
        return ResponseEntity.ok("Service list fetched and saved successfully. Saved " + savedBenefits.size() + " items.");
    }
}