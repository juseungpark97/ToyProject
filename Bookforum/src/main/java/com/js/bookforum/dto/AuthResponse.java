package com.js.bookforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "인증 응답 DTO")
public class AuthResponse {
    @Schema(description = "JWT 토큰")
    private String token;

    @Schema(description = "사용자 이메일")
    private String email;

    @Schema(description = "사용자 역할")
    private String role;
}