package com.charles.ripplecarbackend.controller;

import com.charles.ripplecarbackend.ConfigTest;
import com.charles.ripplecarbackend.model.entity.User;
import com.charles.ripplecarbackend.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "auth@auth.com", password = "123456", roles = "USER")
class UserControllerTest extends ConfigTest {

    private final String domain = "@mail.com";
    private final String randomString = RandomStringUtils.randomAlphanumeric(10);

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
    @DisplayName("Should create user")
    @Order(1)
    void shouldCreateUser() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", randomString.concat(domain));
        data.put("password", randomString);
        data.put("name", randomString);

        mockMvc.perform(post("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();

        Assertions.assertEquals(randomString.concat(domain), users.get(users.size() - 1).getEmail());
    }

    @Test
    @DisplayName("Should get user details")
    @Order(2)
    void shouldGetUserDetails() throws Exception {
        mockMvc.perform(get("/user/details")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();

        Assertions.assertEquals("Auth", users.get(0).getName());
    }

    @Test
    @DisplayName("Should get all user")
    @Order(3)
    @WithMockUser(username = "auth@auth.com", password = "123456", roles = "ADMIN")
    void shouldGetAllUser() throws Exception {
        mockMvc.perform(get("/user")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();

        Assertions.assertEquals(1, users.size());
    }

    @Test
    @DisplayName("Should update user")
    @Order(4)
    void shouldUpdateUser() throws Exception {
        JSONObject data = new JSONObject();
        data.put("email", "auth@auth.com");
        data.put("password", "123456");
        data.put("name", "Auth_Update");

        mockMvc.perform(put("/user")
                        .content(String.valueOf(data))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();

        Assertions.assertEquals("Auth_Update", users.get(0).getName());
    }

    @Test
    @DisplayName("Should search users")
    @Order(5)
    void shouldSearchUser() throws Exception {
        mockMvc.perform(get("/user/search?searchTerm=auth")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.ASC, "name");
        Page<User> search = userRepository.search("auth", pageRequest);

        Assertions.assertEquals(1, search.toList().size());
    }
}
