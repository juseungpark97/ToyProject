package com.js.bookforum.security;

import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey; // JWT 서명에 사용할 비밀 키

    @Value("${jwt.expiration}")
    private long validityInMilliseconds; // 토큰의 유효 시간 (밀리초)

    // JWT 토큰 생성
    public String createToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email); // 토큰 주체 설정 (여기서는 이메일)
        claims.put("role", role); // 추가 클레임으로 역할 정보 저장

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds); // 토큰의 만료 시간 설정

        // SecretKeySpec을 사용하여 비밀 키를 Key 객체로 생성
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256) // 새로운 방식으로 서명
                .compact();
    }

    // JWT 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 예외 처리 로직 (예: 토큰이 만료되었거나 손상된 경우)
            return false;
        }
    }
}