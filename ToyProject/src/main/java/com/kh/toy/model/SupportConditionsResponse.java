package com.kh.toy.model;

import lombok.Data;

import java.util.List;

@Data
public class SupportConditionsResponse {
    private int currentCount;
    private List<SupportConditions> data;
}
