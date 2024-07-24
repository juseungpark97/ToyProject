package com.kh.toy.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.kh.toy.model.Benefits;
import com.kh.toy.model.ServiceListResponse;
import com.kh.toy.repository.BenefitsRepository;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BenefitsRepository benefitsRepository;

    private static final String API_KEY = "kb1eZ6UYEhI0LogWn52MjJe85mnACxRhWFrqaMkh3LpaBL1J5uWyolKueq4ZrV6YSIUJuLQ44mbW2n2rzCMIPQ==";
    private static final String BASE_URL = "https://api.odcloud.kr/api/gov24/v3/serviceList";

    public List<Benefits> fetchAndSaveServiceList(int page, int perPage) {
        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("page", page)
                .queryParam("perPage", perPage)
                .queryParam("returnType", "JSON")
                .queryParam("serviceKey", API_KEY)
                .build()
                .toUri();
        ServiceListResponse response = restTemplate.getForObject(uri, ServiceListResponse.class);
        if (response != null && response.getData() != null) {
            List<Benefits> benefits = response.getData();
            return benefitsRepository.saveAll(benefits);
        } else {
            System.out.println("No data found");
            return new ArrayList<>();
        }
    }
}
