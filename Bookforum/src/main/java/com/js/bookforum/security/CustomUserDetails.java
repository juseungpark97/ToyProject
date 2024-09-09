package com.js.bookforum.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.js.bookforum.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user; // User 엔티티 필드

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> user.getRole().getName()); // 역할에 따른 권한 부여
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // User 엔티티의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // User 엔티티의 이메일 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨있지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화됨
    }

    // Custom 메서드: 원본 User 엔티티를 반환
    public User getUser() {
        return user;
    }
}