package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttendanceTest {
    @Test
    public void testAttendanceGettersSetters() {
        CourseDetails course = new CourseDetails();
        course.setCourseName("Computer Science");

        Subjects subject = new Subjects();
        subject.setSubCode("CS101");

        Attendance attendance = new Attendance();
        attendance.setAbsenteeId(1L);
        attendance.setDate(new Date());
        attendance.setCourse(course);
        attendance.setSubject(subject);
        attendance.setPresent(30);
        attendance.setAbsent(5);
        attendance.setStudentsAbsent(List.of("student1", "student2"));

        assertEquals(1L, attendance.getAbsenteeId());
        assertNotNull(attendance.getDate());
        assertEquals(course, attendance.getCourse());
        assertEquals(subject, attendance.getSubject());
        assertEquals(30, attendance.getPresent());
        assertEquals(5, attendance.getAbsent());
        assertEquals(List.of("student1", "student2"), attendance.getStudentsAbsent());
    }
}
