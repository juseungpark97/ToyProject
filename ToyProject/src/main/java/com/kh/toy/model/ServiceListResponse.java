package com.kh.toy.model;

import lombok.Data;

import java.util.List;

@Data
public class ServiceListResponse {
    private int currentCount;
    private int matchCount;
    private int totalCount; // totalCount 필드 추가
    private int page;
    private int perPage;
    private List<Benefits> data;
}
