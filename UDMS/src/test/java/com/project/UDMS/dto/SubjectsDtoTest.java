package com.project.UDMS.dto;

import com.project.UDMS.entity.ProfessorDetails;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectsDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSubjectsDtoValid() {
        ProfessorDetails professorDetails = new ProfessorDetails();
        professorDetails.setPId("S1");
        professorDetails.setName("Dr. John Smith");

        SubjectsDto subjectsDto = new SubjectsDto();
        subjectsDto.setSubCode("CS101");
        subjectsDto.setSubName("Computer Science");
        subjectsDto.setSubType('C');
        subjectsDto.setProfessorId(professorDetails);

        Set<ConstraintViolation<SubjectsDto>> violations = validator.validate(subjectsDto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testSubjectsDtoInvalid() {
        SubjectsDto subjectsDto = new SubjectsDto();
        subjectsDto.setSubCode("CS101");
        subjectsDto.setSubName(""); // Invalid: blank
        subjectsDto.setSubType(null); // Invalid: null
        subjectsDto.setProfessorId(null); // Invalid: null

        Set<ConstraintViolation<SubjectsDto>> violations = validator.validate(subjectsDto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<SubjectsDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("subName is Compulsory")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("mention its type")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("assign the professor")));
    }
}
