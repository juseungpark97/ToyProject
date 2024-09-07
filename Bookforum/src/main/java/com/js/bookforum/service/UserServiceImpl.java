package com.js.bookforum.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.js.bookforum.entity.Role;
import com.js.bookforum.entity.User;
import com.js.bookforum.repository.RoleRepository;
import com.js.bookforum.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // RoleRepository 의존성 추가
    private final PasswordEncoder passwordEncoder;


    @Override
    public User createUser(User user) {
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
    	
        Role userRole = roleRepository.findByName("USER")
        				.orElseThrow(() -> new RuntimeException("User role not found"));
        User newUser = User.builder()
        				.name(user.getName())
        				.email(user.getEmail())
        				.password(encodedPassword)
        				.registrationDate(new Date())
        				.role(userRole)
        				.build();
       
        
        return userRepository.save(newUser);
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