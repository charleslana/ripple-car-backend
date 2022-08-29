package com.charles.ripplecarbackend.service;

import com.charles.ripplecarbackend.ConfigTest;
import com.charles.ripplecarbackend.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "auth@auth.com", password = "123456", roles = "USER")
class UserServiceTest extends ConfigTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void auth() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "auth@auth.com");
        data.put("password", "123456");
        data.put("name", "Auth");

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should not create user with email already exists")
    @Order(1)
    void shouldNotCreateUserWithEmailAlreadyExists() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "auth@auth.com");
        data.put("password", "123456");
        data.put("name", "Example");

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("user.exists.email", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Should not create user with email name exists")
    @Order(2)
    void shouldNotCreateUserWithNameAlreadyExists() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "auth@example.com");
        data.put("password", "123456");
        data.put("name", "Auth");

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("user.exists.name", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Should get user details")
    @Order(3)
    void shouldGetUserDetails() throws Exception {
        userRepository.deleteAll();

        mockMvc.perform(get("/user/details")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("user.not.exists.email", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Should get all user")
    @Order(4)
    @WithMockUser(username = "auth@auth.com", password = "123456", roles = "ADMIN")
    void shouldGetAllUser() throws Exception {
        mockMvc.perform(get("/user")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should update user")
    @Order(5)
    void shouldUpdateUser() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "exist@example.com");
        data.put("password", "123456");
        data.put("name", "Name_Updated");

        mockMvc.perform(put("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("user_success"));
    }

    @Test
    @DisplayName("Should not update user when name already exists in other user")
    @Order(6)
    void shouldNotUpdateUserWhenNameAlreadyExistsInOtherUser() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "exist@example.com");
        data.put("password", "123456");
        data.put("name", "Name_update");

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        data = new JSONObject();
        data.put("email", "exist@example.com");
        data.put("password", "123456");
        data.put("name", "Name_update");

        mockMvc.perform(put("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("user.exists.name", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Should search user with size 0")
    @Order(7)
    @WithMockUser(username = "auth@auth.com", password = "123456", roles = "ADMIN")
    void shouldSearchUserWithSize0() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "auth2@auth.com");
        data.put("password", "123456");
        data.put("name", "Auth2");

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/search?searchTerm=auth&size=0")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("Should search user with size more than 20")
    @Order(7)
    @WithMockUser(username = "auth@auth.com", password = "123456", roles = "ADMIN")
    void shouldSearchUserWithSizeMoreThan20() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "auth2@auth.com");
        data.put("password", "123456");
        data.put("name", "Auth2");

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/search?searchTerm=auth&size=21")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }
}
