package com.kh.toy.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.toy.model.Member;
import com.kh.toy.service.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.registerMember(member));
    }

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Map<String, String> credentials) {
        Optional<Member> member = memberService.findMemberByUsername(credentials.get("username"));
        if (member.isPresent() && member.get().getPassword().equals(credentials.get("password"))) {
            return ResponseEntity.ok(member.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
