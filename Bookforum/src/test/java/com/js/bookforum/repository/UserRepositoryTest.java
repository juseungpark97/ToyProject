package com.js.bookforum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.js.bookforum.entity.Role;
import com.js.bookforum.entity.User;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role testRole;

    @BeforeEach
    void setUp() {
        // "USER" 역할이 이미 존재하는지 확인
        Optional<Role> existingRole = roleRepository.findByName("USER");
        if (existingRole.isPresent()) {
            testRole = existingRole.get(); // 이미 존재하면 기존 역할 사용
        } else {
            // 존재하지 않으면 새로운 역할 생성 및 저장
            testRole = new Role();
            testRole.setName("USER");
            testRole.setDescription("Standard user role");
            testRole = roleRepository.save(testRole);
        }

        // 사용자 엔터티 준비
        User testUser = User.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .password("password")
                .role(testRole)
                .build();
        userRepository.save(testUser);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("janedoe@example.com");
        user.setPassword("password");
        user.setRole(testRole);

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertEquals("Jane Doe", savedUser.getName());
    }

    @Test
    void testFindByEmail() {
        Optional<User> user = userRepository.findByEmail("johndoe@example.com");
        assertNotNull(user.orElse(null));
        assertEquals("John Doe", user.get().getName());
    }

    @Test
    void testDeleteUser() {
        Optional<User> user = userRepository.findByEmail("johndoe@example.com");
        userRepository.delete(user.orElseThrow());
        Optional<User> deletedUser = userRepository.findByEmail("johndoe@example.com");
        assertEquals(Optional.empty(), deletedUser);
    }
}