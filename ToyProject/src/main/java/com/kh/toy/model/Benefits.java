package com.kh.toy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "Benefits")
public class Benefits {
    @Id
    @JsonProperty("서비스ID")
    private String benefitId;
    
    @JsonProperty("서비스명")
    private String name;
    
    @JsonProperty("부서명")
    private String department;
    
    @JsonProperty("사용자구분")
    private String userType;
    
    @JsonProperty("상세조회URL")
    private String detailUrl;
    
    @JsonProperty("서비스목적요약")
    private String summary;
    
    @JsonProperty("서비스분야")
    private String area;
    
    @JsonProperty("소관기관명")
    private String agencyName;
    
    @JsonProperty("소관기관유형")
    private String agencyType;
    
    @JsonProperty("소관기관코드")
    private String agencyCode;
    
    @JsonProperty("신청기한")
    private String applicationPeriod;
    
    @JsonProperty("신청방법")
    private String applicationMethod;
    
    @JsonProperty("전화문의")
    private String phoneInquiry;
    
    @JsonProperty("접수기관")
    private String receivingAgency;
    
    @JsonProperty("조회수")
    private int views;
    
    @JsonProperty("지원내용")
    private String supportDetails;
    
    @JsonProperty("지원대상")
    private String supportTarget;
    
    @JsonProperty("지원유형")
    private String supportType;
}