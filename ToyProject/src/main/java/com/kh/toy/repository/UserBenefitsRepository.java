package com.kh.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.toy.model.UserBenefits;

import java.util.List;

public interface UserBenefitsRepository extends JpaRepository<UserBenefits, Long> {
    List<UserBenefits> findByMemberMemberId(Long memberId);
}
