package com.js.bookforum.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.js.bookforum.entity.Role;
import com.js.bookforum.entity.User;
import com.js.bookforum.repository.RoleRepository;
import com.js.bookforum.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setName("USER");

        testUser = User.builder()
                .userId(1L)
                .name("John Doe")
                .email("johndoe@example.com")
                .password("password")
                .registrationDate(new Date())
                .role(testRole)
                .build();
    }

    @Test
    void testCreateUser() {
        // given
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(testRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setUserId(1L); // ID 설정 (DB에 저장된 것처럼)
            return user;
        });

        // when
        User createdUser = userService.createUser(testUser);

        // then
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("johndoe@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        assertEquals(testRole, createdUser.getRole());
    }

    @Test
    void testGetUserById() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // when
        User foundUser = userService.getUserById(1L);

        // then
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    void testGetAllUsers() {
        // given
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // when
        List<User> users = userService.getAllUsers();

        // then
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
    }

    @Test
    void testUpdateUser() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updateUser = User.builder()
                .name("Jane Doe")
                .email("janedoe@example.com")
                .password("newpassword")
                .build();

        // when
        User updatedUser = userService.updateUser(1L, updateUser);

        // then
        assertNotNull(updatedUser);
        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("janedoe@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        // when
        userService.deleteUser(1L);

        // then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testFindByEmail() {
        // given
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(testUser));

        // when
        User foundUser = userService.findByEmail("johndoe@example.com");

        // then
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }
}