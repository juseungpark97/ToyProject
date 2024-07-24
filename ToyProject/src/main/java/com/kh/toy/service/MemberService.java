package com.kh.toy.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.toy.model.Member;
import com.kh.toy.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
