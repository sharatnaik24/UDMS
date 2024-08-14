package com.project.UDMS.repository;

import com.project.UDMS.entity.ProfessorDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProfessorRepositoryTest {

    @Mock
    private ProfessorRepository professorRepository;

    private ProfessorDetails professor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        professor = new ProfessorDetails();
        professor.setPId("S1");
        professor.setName("Dr. John Doe");
        professor.setQualification("PhD in Computer Science");
        professor.setMail("johndoe@example.com");
        professor.setMob("1234567890");
    }

    @Test
    public void testFindByIdSuccess() {
        when(professorRepository.findById("S1")).thenReturn(Optional.of(professor));

        Optional<ProfessorDetails> foundProfessor = professorRepository.findById("S1");

        assertTrue(foundProfessor.isPresent());
        assertEquals("Dr. John Doe", foundProfessor.get().getName());
        assertEquals("PhD in Computer Science", foundProfessor.get().getQualification());
        assertEquals("johndoe@example.com", foundProfessor.get().getMail());
        assertEquals("1234567890", foundProfessor.get().getMob());
    }

    @Test
    public void testFindByIdNotFound() {
        when(professorRepository.findById("S2")).thenReturn(Optional.empty());

        Optional<ProfessorDetails> foundProfessor = professorRepository.findById("S2");

        assertFalse(foundProfessor.isPresent());
    }

    @Test
    public void testSaveProfessor() {
        when(professorRepository.save(any(ProfessorDetails.class))).thenReturn(professor);

        ProfessorDetails savedProfessor = professorRepository.save(professor);

        assertNotNull(savedProfessor);
        assertEquals("Dr. John Doe", savedProfessor.getName());
        assertEquals("PhD in Computer Science", savedProfessor.getQualification());
        assertEquals("johndoe@example.com", savedProfessor.getMail());
        assertEquals("1234567890", savedProfessor.getMob());
    }

    @Test
    public void testDeleteProfessor() {
        doNothing().when(professorRepository).delete(professor);

        professorRepository.delete(professor);

        verify(professorRepository, times(1)).delete(professor);
    }
}
