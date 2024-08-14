package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDetailsTest {
    @Test
    public void testCourseDetailsGettersSetters() {
        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        assertEquals("Computer Science", course.getCourseName());
    }

    @Test
    public void testCourseSubjectsRelationship() {
        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        Subjects subject = new Subjects();
        subject.setSubCode("CS101");

        course.setSubjects(List.of(subject));

        assertEquals(List.of(subject), course.getSubjects());
    }
}
