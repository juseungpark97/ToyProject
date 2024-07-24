package com.kh.toy.model;

import lombok.Data;

import java.util.List;

@Data
public class ServiceListResponse {
    private int currentCount;
    private List<Benefits> data;
}
