package com.charles.ripplecarbackend.dto;

import com.charles.ripplecarbackend.ConfigTest;
import com.charles.ripplecarbackend.model.dto.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDTOTest extends ConfigTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should validate user dto with success")
    @Order(1)
    void shouldValidateUserDTOWithSuccess() {
        UserDTO dto = new UserDTO();
        dto.setEmail("example@example.com");
        dto.setPassword("123456");
        dto.setName("example");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    @DisplayName("Should validate user dto email is invalid")
    @Order(2)
    void shouldValidateUserDTOEmailIsInvalid() {
        UserDTO dto = new UserDTO();
        dto.setEmail("example");
        dto.setPassword("123456");
        dto.setName("example");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Should validate user dto name is invalid")
    @Order(3)
    void shouldValidateUserDTONameIsInvalid() {
        UserDTO dto = new UserDTO();
        dto.setEmail("example@example.com");
        dto.setPassword("123456");
        dto.setName("example name");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Should validate user dto name is short")
    @Order(4)
    void shouldValidateUserDTONameIsShort() {
        UserDTO dto = new UserDTO();
        dto.setEmail("example@example.com");
        dto.setPassword("123456");
        dto.setName(RandomStringUtils.randomAlphanumeric(2));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    @Test
    @DisplayName("Should validate user dto name is big")
    @Order(5)
    void shouldValidateUserDTONameIsBig() {
        UserDTO dto = new UserDTO();
        dto.setEmail("example@example.com");
        dto.setPassword("123456");
        dto.setName(RandomStringUtils.randomAlphanumeric(21));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    @Test
    @DisplayName("Should validate user dto password is short")
    @Order(6)
    void shouldValidateUserDTOPasswordIsShort() {
        UserDTO dto = new UserDTO();
        dto.setEmail("example@example.com");
        dto.setPassword(RandomStringUtils.randomAlphanumeric(5));
        dto.setName("example");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }
}
