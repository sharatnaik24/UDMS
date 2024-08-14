package com.project.UDMS.repository;

import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.CourseDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentEnrollmentRepositoryTest {

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    private StudentEnrollment studentEnrollment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentEnrollment = new StudentEnrollment();
        studentEnrollment.setRollNo("R001");
        studentEnrollment.setName("John Doe");
        studentEnrollment.setMail("johndoe@example.com");
        studentEnrollment.setMob("1234567890");

        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseName("Computer Science");
        studentEnrollment.setCourse(courseDetails);
    }

    @Test
    public void testFindByIdSuccess() {
        when(studentEnrollmentRepository.findById("R001")).thenReturn(Optional.of(studentEnrollment));

        Optional<StudentEnrollment> foundEnrollment = studentEnrollmentRepository.findById("R001");

        assertTrue(foundEnrollment.isPresent());
        assertEquals("John Doe", foundEnrollment.get().getName());
        assertEquals("johndoe@example.com", foundEnrollment.get().getMail());
    }

    @Test
    public void testFindByIdNotFound() {
        when(studentEnrollmentRepository.findById("R002")).thenReturn(Optional.empty());

        Optional<StudentEnrollment> foundEnrollment = studentEnrollmentRepository.findById("R002");

        assertFalse(foundEnrollment.isPresent());
    }

    @Test
    public void testSaveStudentEnrollment() {
        when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(studentEnrollment);

        StudentEnrollment savedEnrollment = studentEnrollmentRepository.save(studentEnrollment);

        assertNotNull(savedEnrollment);
        assertEquals("John Doe", savedEnrollment.getName());
        assertEquals("johndoe@example.com", savedEnrollment.getMail());
    }

    @Test
    public void testDeleteStudentEnrollment() {
        doNothing().when(studentEnrollmentRepository).delete(studentEnrollment);

        studentEnrollmentRepository.delete(studentEnrollment);

        verify(studentEnrollmentRepository, times(1)).delete(studentEnrollment);
    }
}
