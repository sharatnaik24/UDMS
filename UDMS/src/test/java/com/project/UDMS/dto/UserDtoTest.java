package com.project.UDMS.dto;

import com.project.UDMS.resource.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private UserDto userDto;

    @BeforeAll
    public static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
    }

    @Test
    public void testUserDtoValid() {
        userDto.setId(1);
        userDto.setUserId("John Doe");
        userDto.setUsername("johndoe");
        userDto.setPassword("password123");
        userDto.setRole(Role.STUDENT);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUserDtoNameNotBlank() {
        userDto.setId(1);
        userDto.setUserId("johndoe");
        userDto.setPassword("password123");
        userDto.setRole(Role.STUDENT);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDto> violation = violations.iterator().next();
        assertEquals("must not be blank", violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
    }

    @Test
    public void testUserDtoUsernameNotBlank() {
        userDto.setId(1);
        userDto.setUserId("John Doe");
        userDto.setPassword("password123");
        userDto.setRole(Role.STUDENT);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDto> violation = violations.iterator().next();
        assertEquals("must not be blank", violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
    }

    @Test
    public void testUserDtoPasswordNotBlank() {
        userDto.setId(1);
        userDto.setUserId("John Doe");
        userDto.setUsername("johndoe");
        userDto.setRole(Role.STUDENT);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDto> violation = violations.iterator().next();
        assertEquals("must not be blank", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @Test
    public void testUserDtoRoleNotNull() {
        userDto.setId(1);
        userDto.setUserId("John Doe");
        userDto.setUsername("johndoe");
        userDto.setPassword("password123");

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UserDto> violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("role", violation.getPropertyPath().toString());
    }
}
