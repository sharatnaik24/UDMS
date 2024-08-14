package com.project.UDMS.dto;

import com.project.UDMS.entity.Subjects;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCourseDtoValid() {
        Subjects subject1 = new Subjects();
        subject1.setSubCode("CS101");
        subject1.setSubName("Computer Science Basics");

        Subjects subject2 = new Subjects();
        subject2.setSubCode("CS102");
        subject2.setSubName("Data Structures");

        Subjects subject3 = new Subjects();
        subject3.setSubCode("CS103");
        subject3.setSubName("Algorithms");

        Subjects subject4 = new Subjects();
        subject4.setSubCode("CS104");
        subject4.setSubName("Operating Systems");

        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");
        courseDto.setSubjects(List.of(subject1, subject2, subject3, subject4));

        Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testCourseDtoInvalid() {
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName("Computer Science");

        Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<CourseDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("enter 4 subjects")));
    }
}

