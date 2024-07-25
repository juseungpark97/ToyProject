package com.kh.toy.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${api.key}")
    private String apiKey;
    
    private static final String BASE_URL = "https://api.odcloud.kr/api/gov24/v3/serviceList";

    public List<Benefits> fetchLimitedServiceList() {
        int totalCount = 700; // 총 가져올 데이터 수
        int pageSize = 80; // 한 번에 요청할 데이터 수
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
        List<Benefits> allBenefits = new ArrayList<>();
        
        for (int page = 1; page <= totalPages; page++) {
            int currentPageSize = Math.min(pageSize, totalCount - (page - 1) * pageSize);
            URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("page", page)
                    .queryParam("perPage", currentPageSize)
                    .queryParam("serviceKey", apiKey)
                    .build()
                    .toUri();
            ServiceListResponse response = restTemplate.getForObject(uri, ServiceListResponse.class);
            if (response != null && response.getData() != null) {
                allBenefits.addAll(response.getData());
            }
            // Break if we have collected enough data
            if (allBenefits.size() >= totalCount) {
                break;
            }
        }
        // Trim the list to the desired size if more data was fetched
        if (allBenefits.size() > totalCount) {
            allBenefits = allBenefits.subList(0, totalCount);
        }
        return allBenefits;
    }

    public void saveServiceList(List<Benefits> benefits) {
        benefitsRepository.saveAll(benefits);
    }
}