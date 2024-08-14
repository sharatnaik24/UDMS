package com.project.UDMS.dto;

import com.project.UDMS.entity.StudentEnrollment;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StudentMarksDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testStudentMarksDtoValid() {
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setRollNo("123");
        studentEnrollment.setName("John Doe");

        StudentMarksDto studentMarksDto = new StudentMarksDto();
        studentMarksDto.setMId(1);
        studentMarksDto.setRegno(studentEnrollment);
        studentMarksDto.setMarks1(85);
        studentMarksDto.setMarks2(90);
        studentMarksDto.setMarks3(88);
        studentMarksDto.setMarks4(92);

        Set<ConstraintViolation<StudentMarksDto>> violations = validator.validate(studentMarksDto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testStudentMarksDtoInvalid() {
        StudentMarksDto studentMarksDto = new StudentMarksDto();
        studentMarksDto.setRegno(null);
        studentMarksDto.setMarks1(105);
        studentMarksDto.setMarks2(110);
        studentMarksDto.setMarks3(115);
        studentMarksDto.setMarks4(120);

        Set<ConstraintViolation<StudentMarksDto>> violations = validator.validate(studentMarksDto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<StudentMarksDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(5, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("enter the regno properly")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("marks must be less than or equal to 100")));
    }
}
