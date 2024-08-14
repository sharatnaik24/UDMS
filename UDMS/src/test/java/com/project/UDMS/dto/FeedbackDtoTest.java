package com.project.UDMS.dto;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.ProfessorDetails;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFeedbackDtoValid() {
        ProfessorDetails professorDetails = new ProfessorDetails();
        professorDetails.setPId("S1");
        professorDetails.setName("Dr. Jane Doe");
        professorDetails.setQualification("PhD");
        professorDetails.setMail("jane.doe@example.com");
        professorDetails.setMob("9876543210");

        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseName("Computer Science");

        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setFID(1);
        feedbackDto.setProffesor(professorDetails);
        feedbackDto.setCoursename(courseDetails);
        feedbackDto.setFeedback("Great course!");

        Set<ConstraintViolation<FeedbackDto>> violations = validator.validate(feedbackDto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testFeedbackDtoInvalid() {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setFID(1);

        Set<ConstraintViolation<FeedbackDto>> violations = validator.validate(feedbackDto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<FeedbackDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("atleast you must enter your coursename")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("writeyour complaint/feedback")));
    }
}
