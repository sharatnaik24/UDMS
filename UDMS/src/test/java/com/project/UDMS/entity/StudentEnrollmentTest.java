package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEnrollmentTest {
    @Test
    public void testStudentEnrollmentGettersSetters() {
        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        StudentEnrollment student = new StudentEnrollment();
        student.setRollNo("123");
        student.setName("John Doe");
        student.setMail("john.doe@example.com");
        student.setMob("1234567890");
        student.setCourse(course);

        assertEquals("123", student.getRollNo());
        assertEquals("John Doe", student.getName());
        assertEquals("john.doe@example.com", student.getMail());
        assertEquals("1234567890", student.getMob());
        assertEquals(course, student.getCourse());
    }
}
