package com.js.bookforum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.js.bookforum.entity.User;
import com.js.bookforum.repository.UserRepository;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository; // UserRepository를 모킹합니다.

    @InjectMocks
    private UserServiceImpl userService; // UserServiceImpl을 테스트합니다.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 모킹 초기화
    }

    @Test
    void testCreateUser() {
        // 테스트할 User 객체 생성
        User user = new User();
        user.setEmail("test@example.com");

        // userRepository.save() 호출 시 user 객체를 반환하도록 설정
        when(userRepository.save(user)).thenReturn(user);

        // UserServiceImpl의 createUser 메서드를 호출하여 테스트 수행
        User createdUser = userService.createUser(user);

        // 결과 검증
        assertEquals(user.getEmail(), createdUser.getEmail()); // 생성된 사용자의 이메일 검증
        verify(userRepository, times(1)).save(user); // userRepository의 save() 메서드가 한 번 호출되었는지 검증
    }

    @Test
    void testGetUserById_UserExists() {
        // 테스트할 User 객체 생성
        User user = new User();
        user.setUserId(1L);

        // userRepository.findById() 호출 시 Optional.of(user) 반환하도록 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // UserServiceImpl의 getUserById 메서드를 호출하여 테스트 수행
        User foundUser = userService.getUserById(1L);

        // 결과 검증
        assertEquals(user.getUserId(), foundUser.getUserId()); // 조회된 사용자의 ID 검증
        verify(userRepository, times(1)).findById(1L); // userRepository의 findById() 메서드가 한 번 호출되었는지 검증
    }

    @Test
    void testGetUserById_UserNotFound() {
        // userRepository.findById() 호출 시 Optional.empty() 반환하도록 설정
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // UserServiceImpl의 getUserById 메서드가 RuntimeException을 던지는지 테스트
        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));

        // userRepository의 findById() 메서드가 한 번 호출되었는지 검증
        verify(userRepository, times(1)).findById(1L);
    }
}