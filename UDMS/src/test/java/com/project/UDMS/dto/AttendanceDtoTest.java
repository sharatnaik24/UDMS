package com.project.UDMS.dto;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.Subjects;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AttendanceDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAttendanceDtoValid() {
        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        Subjects subject = new Subjects();
        subject.setSubCode("CS101");

        AttendanceDto dto = new AttendanceDto();
        dto.setDate(new Date());
        dto.setCourse(course);
        dto.setSubject(subject);
        dto.setPresent(30);
        dto.setAbsent(5);
        dto.setStudentsAbsent(List.of("student1", "student2"));

        Set<ConstraintViolation<AttendanceDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testAttendanceDtoInvalid() {
        AttendanceDto dto = new AttendanceDto();

        Set<ConstraintViolation<AttendanceDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<AttendanceDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Date cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Course name cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Subject ID cannot be null")));
        assertTrue(dto.getPresent()==0);
        assertTrue(dto.getAbsent()==0);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("List of absent students cannot be null")));
    }

    @Test
    public void testAttendanceDtoPartialInvalid() {
        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        AttendanceDto dto = new AttendanceDto();
        dto.setDate(new Date());
        dto.setCourse(course);
        dto.setPresent(30);
        dto.setAbsent(3);

        Set<ConstraintViolation<AttendanceDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<AttendanceDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Subject ID cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("List of absent students cannot be null")));
    }
}
