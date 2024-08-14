package com.project.UDMS.repository;

import com.project.UDMS.entity.Subjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectRepositoryTest {

    @Mock
    private SubjectRepository subjectRepository;

    private Subjects subject;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        subject = new Subjects();
        subject.setSubCode("MATH101");
        subject.setSubName("Mathematics");
    }

    @Test
    public void testSaveSubject() {
        when(subjectRepository.save(any(Subjects.class))).thenReturn(subject);
        Subjects savedSubject = subjectRepository.save(subject);
        assertNotNull(savedSubject);
        assertEquals("MATH101", savedSubject.getSubCode());
        assertEquals("Mathematics", savedSubject.getSubName());
    }

    @Test
    public void testFindBySubCodeSuccess() {
        when(subjectRepository.findById("MATH101")).thenReturn(Optional.of(subject));
        Optional<Subjects> foundSubject = subjectRepository.findById("MATH101");
        assertTrue(foundSubject.isPresent());
        assertEquals("MATH101", foundSubject.get().getSubCode());
    }

    @Test
    public void testFindBySubCodeNotFound() {
        when(subjectRepository.findById("PHY101")).thenReturn(Optional.empty());
        Optional<Subjects> foundSubject = subjectRepository.findById("PHY101");
        assertFalse(foundSubject.isPresent());
    }

    @Test
    public void testDeleteSubject() {
        doNothing().when(subjectRepository).deleteById("MATH101");
        subjectRepository.deleteById("MATH101");
        verify(subjectRepository, times(1)).deleteById("MATH101");
    }
}
