package com.js.bookforum.service;

import com.js.bookforum.entity.User;
import java.util.List;

public interface UserService {
    User createUser(User user); // 사용자 생성
    User getUserById(Long id);  // ID로 사용자 조회
    List<User> getAllUsers();   // 모든 사용자 조회
    User updateUser(Long id, User user); // 사용자 정보 수정
    void deleteUser(Long id);   // 사용자 삭제
    User findByEmail(String email); // 이메일로 사용자 조회
}