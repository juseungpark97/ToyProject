package com.kh.toy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.toy.model.UserBenefits;
import com.kh.toy.service.UserBenefitService;

@RestController
@RequestMapping("/api/userbenefits")
public class UserBenefitController {
    @Autowired
    private UserBenefitService userBenefitService;

    @GetMapping("/{memberId}")
    public List<UserBenefits> getUserBenefits(@PathVariable Long memberId) {
        return userBenefitService.getUserBenefits(memberId);
    }

    @PostMapping
    public UserBenefits saveUserBenefit(@RequestBody UserBenefits userBenefits) {
        return userBenefitService.saveUserBenefit(userBenefits);
    }
}
