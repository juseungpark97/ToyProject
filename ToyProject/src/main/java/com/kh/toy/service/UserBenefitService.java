package com.kh.toy.service;

import com.kh.toy.model.UserBenefits;
import com.kh.toy.repository.UserBenefitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBenefitService {

    @Autowired
    private UserBenefitsRepository userBenefitsRepository;

    public List<UserBenefits> getUserBenefits(Long memberId) {
        return userBenefitsRepository.findByMemberMemberId(memberId);
    }

    public UserBenefits saveUserBenefit(UserBenefits userBenefits) {
        return userBenefitsRepository.save(userBenefits);
    }
}
