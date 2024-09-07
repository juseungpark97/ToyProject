package com.js.bookforum.service;

import com.js.bookforum.entity.User;
import com.js.bookforum.repository.UserRepository;
import com.js.bookforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // 새로운 사용자 생성
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        // ID로 사용자 조회
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        // 모든 사용자 조회
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        // 사용자 정보 수정
        User existingUser = getUserById(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        // 사용자 삭제
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        // 이메일로 사용자 조회
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}