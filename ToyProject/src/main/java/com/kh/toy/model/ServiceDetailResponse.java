package com.kh.toy.model;

import lombok.Data;

import java.util.List;

@Data
public class ServiceDetailResponse {
    private int currentCount;
    private List<BenefitDetails> data;
}
