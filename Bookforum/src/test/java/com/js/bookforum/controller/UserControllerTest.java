package com.js.bookforum.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.js.bookforum.entity.User;
import com.js.bookforum.security.CustomUserDetailsService;
import com.js.bookforum.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService; // UserDetailsService 모킹

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .userId(1L)
                .name("John Doe")
                .email("johndoe@example.com")
                .password("password")
                .build();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateUser() throws Exception {
        // given
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        // when & then
        mockMvc.perform(post("/api/users")
                .with(csrf()) // CSRF 토큰 추가
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetUserById() throws Exception {
        // given
        when(userService.getUserById(1L)).thenReturn(testUser);

        // when & then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetAllUsers() throws Exception {
        // given
        when(userService.getAllUsers()).thenReturn(Arrays.asList(testUser));

        // when & then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateUser() throws Exception {
        // given
        User updatedUser = User.builder().userId(1L).name("Jane Doe").email("janedoe@example.com").build();
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        // when & then
        mockMvc.perform(put("/api/users/1")
                .with(csrf()) // CSRF 토큰 추가
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("janedoe@example.com"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteUser() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/users/1")
                .with(csrf())) // CSRF 토큰 추가
                .andExpect(status().isOk());
        
        verify(userService, times(1)).deleteUser(1L);
    }
}