package com.kh.toy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.toy.model.Benefits;
import com.kh.toy.service.ApiService;

@RestController
public class ApiController {
    @Autowired
    private ApiService apiService;

    @GetMapping("/api/fetchAllServiceList")
    public List<Benefits> fetchAllServiceList() {
        List<Benefits> benefits = apiService.fetchLimitedServiceList();
        apiService.saveServiceList(benefits);
        return benefits;
    }
}