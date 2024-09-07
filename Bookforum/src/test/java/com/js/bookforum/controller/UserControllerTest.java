package com.js.bookforum.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.js.bookforum.entity.User;
import com.js.bookforum.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType("application/json")
                .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().isOk());
    }
}