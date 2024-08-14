package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectsTest {
    @Test
    public void testSubjectsGettersSetters() {
        ProfessorDetails professor = new ProfessorDetails();
        professor.setPId("S1");

        Subjects subject = new Subjects();
        subject.setSubCode("CS101");
        subject.setSubName("Introduction to Computer Science");
        subject.setSubType('C');
        subject.setProfessorId(professor);

        assertEquals("CS101", subject.getSubCode());
        assertEquals("Introduction to Computer Science", subject.getSubName());
        assertEquals(Character.valueOf('C'), subject.getSubType());
        assertEquals(professor, subject.getProfessorId());
    }
}
