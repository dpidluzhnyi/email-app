package com.userservice.controller;

import com.userservice.dto.UserData;
import com.userservice.entity.User;
import com.userservice.exception.FailedToCreateUserException;
import com.userservice.exception.NoSuchUserException;
import com.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserData validUserData() {
        UserData data = new UserData();
        data.setFullName("John Doe");
        data.setEmail("john@example.com");
        data.setPhone("1234567890");
        data.setUserName("johndoe");
        return data;
    }

    @Test
    void createUserSuccessfully() throws Exception {
        UserData data = validUserData();
        User user = new User(1L, "johndoe", "John Doe", "john@example.com", "1234567890");
        when(userService.createUser(any(UserData.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("johndoe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createUserFails() throws Exception {
        UserData data = validUserData();
        when(userService.createUser(any(UserData.class)))
                .thenThrow(new FailedToCreateUserException("Unable to create new User"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void returnsListOfUsersSuccessfully() throws Exception {
        User user1 = new User(1L, "alice", "Alice Black", "alice@example.com", "111");
        User user2 = new User(2L, "bob", "Bob Brown", "bob@example.com", "222");
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userName").value("alice"))
                .andExpect(jsonPath("$[1].userName").value("bob"));
    }

    @Test
    void getUserByEmailSuccessfully() throws Exception {
        User user = new User(1L, "johndoe", "John Doe", "john@example.com", "1234567890");
        when(userService.getUserByEmail(eq("john@example.com"))).thenReturn(user);

        mockMvc.perform(get("/api/users/search")
                        .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserByEmailNotFound() throws Exception {
        when(userService.getUserByEmail(eq("unknown@example.com")))
                .thenThrow(new NoSuchUserException("No user found with specified email"));

        mockMvc.perform(get("/api/users/search")
                        .param("email", "unknown@example.com"))
                .andExpect(status().is4xxClientError());
    }
}
