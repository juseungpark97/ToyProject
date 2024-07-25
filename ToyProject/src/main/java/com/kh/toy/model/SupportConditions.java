package com.kh.toy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "SupportConditions")
public class SupportConditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionId;

    @ManyToOne
    @JoinColumn(name = "benefit_id")
    @JsonProperty("benefit_id")
    private Benefits benefits;

    @JsonProperty("JA0101")
    private String JA0101;

    @JsonProperty("JA0102")
    private String JA0102;

    @JsonProperty("JA0110")
    private int JA0110;

    @JsonProperty("JA0111")
    private int JA0111;

    @JsonProperty("JA0201")
    private String JA0201;

    @JsonProperty("JA0202")
    private String JA0202;

    @JsonProperty("JA0203")
    private String JA0203;

    @JsonProperty("JA0204")
    private String JA0204;

    @JsonProperty("JA0205")
    private String JA0205;

    @JsonProperty("JA0301")
    private String JA0301;

    @JsonProperty("JA0302")
    private String JA0302;

    @JsonProperty("JA0303")
    private String JA0303;

    @JsonProperty("JA0313")
    private String JA0313;

    @JsonProperty("JA0314")
    private String JA0314;

    @JsonProperty("JA0315")
    private String JA0315;

    @JsonProperty("JA0316")
    private String JA0316;

    @JsonProperty("JA0317")
    private String JA0317;

    @JsonProperty("JA0318")
    private String JA0318;

    @JsonProperty("JA0319")
    private String JA0319;

    @JsonProperty("JA0320")
    private String JA0320;

    @JsonProperty("JA0322")
    private String JA0322;

    @JsonProperty("JA0326")
    private String JA0326;

    @JsonProperty("JA0327")
    private String JA0327;

    @JsonProperty("JA0328")
    private String JA0328;

    @JsonProperty("JA0329")
    private String JA0329;

    @JsonProperty("JA0330")
    private String JA0330;

    @JsonProperty("JA0401")
    private String JA0401;

    @JsonProperty("JA0402")
    private String JA0402;

    @JsonProperty("JA0403")
    private String JA0403;

    @JsonProperty("JA0404")
    private String JA0404;

    @JsonProperty("JA0410")
    private String JA0410;

    @JsonProperty("JA0411")
    private String JA0411;

    @JsonProperty("JA0412")
    private String JA0412;

    @JsonProperty("JA0413")
    private String JA0413;

    @JsonProperty("JA0414")
    private String JA0414;

    @JsonProperty("JA1101")
    private String JA1101;

    @JsonProperty("JA1102")
    private String JA1102;

    @JsonProperty("JA1103")
    private String JA1103;

    @JsonProperty("JA1201")
    private String JA1201;

    @JsonProperty("JA1202")
    private String JA1202;

    @JsonProperty("JA1299")
    private String JA1299;

    @JsonProperty("JA2101")
    private String JA2101;

    @JsonProperty("JA2102")
    private String JA2102;

    @JsonProperty("JA2103")
    private String JA2103;

    @JsonProperty("JA2201")
    private String JA2201;

    @JsonProperty("JA2202")
    private String JA2202;

    @JsonProperty("JA2203")
    private String JA2203;

    @JsonProperty("JA2299")
    private String JA2299;
}
