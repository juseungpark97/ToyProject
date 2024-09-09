package com.js.bookforum.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.js.bookforum.dto.ErrorResponse;
import com.js.bookforum.dto.LoginRequest;
import com.js.bookforum.security.JwtTokenProvider;
import com.js.bookforum.service.UserService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@naver.com");
        loginRequest.setPassword("testpassword");
    }

    @Test
    void testLoginSuccess() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.createToken(anyString(), anyString())).thenReturn("dummy-token");

        // when
        ResponseEntity<?> response = authController.login(loginRequest);

        // then
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testLoginFailure() {
        // given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // when
        ResponseEntity<?> response = authController.login(loginRequest);

        // then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("로그인 실패: 이메일이나 비밀번호가 올바르지 않습니다.", ((ErrorResponse) response.getBody()).getMessage());
    }
}