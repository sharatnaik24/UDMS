package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentMarksTest {
    @Test
    public void testStudentMarksGettersSetters() {
        StudentEnrollment student = new StudentEnrollment();
        student.setRollNo("123");

        StudentMarks studentMarks = new StudentMarks();
        studentMarks.setMId(1);
        studentMarks.setRegno(student);
        studentMarks.setMarks1(90);
        studentMarks.setMarks2(85);
        studentMarks.setMarks3(88);
        studentMarks.setMarks4(92);

        assertEquals(1, studentMarks.getMId());
        assertEquals(student, studentMarks.getRegno());
        assertEquals(90, studentMarks.getMarks1());
        assertEquals(85, studentMarks.getMarks2());
        assertEquals(88, studentMarks.getMarks3());
        assertEquals(92, studentMarks.getMarks4());
    }
}
