package com.kh.toy.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class UserBenefits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userBenefitId;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    @ManyToOne
    @JoinColumn(name = "benefit_id")
    private Benefits benefits;
}
