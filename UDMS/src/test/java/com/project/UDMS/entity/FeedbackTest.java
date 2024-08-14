package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {
    @Test
    public void testFeedbackGettersSetters() {
        ProfessorDetails professor = new ProfessorDetails();
        professor.setPId("S1");

        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        Feedback feedback = new Feedback();
        feedback.setFID(1);
        feedback.setProffesor(professor);
        feedback.setCoursename(course);
        feedback.setFeedback("Excellent course!");

        assertEquals(1, feedback.getFID());
        assertEquals(professor, feedback.getProffesor());
        assertEquals(course, feedback.getCoursename());
        assertEquals("Excellent course!", feedback.getFeedback());
    }
}
