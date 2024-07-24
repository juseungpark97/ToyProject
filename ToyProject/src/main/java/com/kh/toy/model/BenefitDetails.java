package com.kh.toy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class BenefitDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    @ManyToOne
    @JoinColumn(name = "benefit_id")
    @JsonProperty("benefit_id")
    private Benefits benefits;

    @JsonProperty("documents_required")
    private String documentsRequired;

    @JsonProperty("inquiry")
    private String inquiry;

    @JsonProperty("legal_basis")
    private String legalBasis;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("criteria")
    private String criteria;

    @JsonProperty("agency")
    private String agency;

    @JsonProperty("update_time")
    private Date updateTime;

    @JsonProperty("application_deadline")
    private String applicationDeadline;

    @JsonProperty("application_url")
    private String applicationUrl;
}
