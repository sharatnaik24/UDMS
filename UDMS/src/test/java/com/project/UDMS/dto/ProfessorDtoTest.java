package com.project.UDMS.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessorDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testProfessorDtoValid() {
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setPId("S1");
        professorDto.setName("Dr. John Smith");
        professorDto.setQualification("PhD in Computer Science");
        professorDto.setMail("john.smith@example.com");
        professorDto.setMob("1234567890");

        Set<ConstraintViolation<ProfessorDto>> violations = validator.validate(professorDto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testProfessorDtoInvalid() {
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setName(""); // Invalid: blank
        professorDto.setQualification(""); // Invalid: blank
        professorDto.setMail("invalid-email"); // Invalid: not an email
        professorDto.setMob("123"); // Invalid: not 10 digits

        Set<ConstraintViolation<ProfessorDto>> violations = validator.validate(professorDto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<ProfessorDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("this fields cant be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must be a well-formed email address")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("invalid mob number")));
    }
}
