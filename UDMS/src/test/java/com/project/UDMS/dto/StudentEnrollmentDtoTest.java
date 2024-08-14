package com.project.UDMS.dto;

import com.project.UDMS.entity.CourseDetails;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEnrollmentDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testStudentEnrollmentDtoValid() {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseName("CS101");

        StudentEnrollmentDto studentEnrollmentDto = new StudentEnrollmentDto();
        studentEnrollmentDto.setRollNo("123");
        studentEnrollmentDto.setName("John Doe");
        studentEnrollmentDto.setMail("john.doe@example.com");
        studentEnrollmentDto.setMob("1234567890");
        studentEnrollmentDto.setCourse(courseDetails);

        Set<ConstraintViolation<StudentEnrollmentDto>> violations = validator.validate(studentEnrollmentDto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testStudentEnrollmentDtoInvalid() {
        StudentEnrollmentDto studentEnrollmentDto = new StudentEnrollmentDto();
        studentEnrollmentDto.setName(""); // Invalid: blank
        studentEnrollmentDto.setMail("invalid-email"); // Invalid: not an email
        studentEnrollmentDto.setMob("123"); // Invalid: not 10 digits
        studentEnrollmentDto.setCourse(null); // Invalid: null

        Set<ConstraintViolation<StudentEnrollmentDto>> violations = validator.validate(studentEnrollmentDto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<StudentEnrollmentDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("name is compulsary")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must be a well-formed email address")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("invalid mob number")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("this field cant be blank")));
    }
}
