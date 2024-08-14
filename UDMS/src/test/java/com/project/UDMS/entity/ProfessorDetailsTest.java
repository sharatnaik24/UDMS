package com.project.UDMS.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessorDetailsTest {
    @Test
    public void testProfessorDetailsGettersSetters() {
        ProfessorDetails professorDetails = new ProfessorDetails();
        professorDetails.setPId("S1");
        professorDetails.setName("Dr. John Smith");
        professorDetails.setQualification("PhD in Computer Science");
        professorDetails.setMail("john.smith@example.com");
        professorDetails.setMob("1234567890");

        assertEquals(1, professorDetails.getPId());
        assertEquals("Dr. John Smith", professorDetails.getName());
        assertEquals("PhD in Computer Science", professorDetails.getQualification());
        assertEquals("john.smith@example.com", professorDetails.getMail());
        assertEquals("1234567890", professorDetails.getMob());
    }
}
