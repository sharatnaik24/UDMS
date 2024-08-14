package com.project.UDMS.repository;

import com.project.UDMS.dto.StudentMarksDto;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.StudentMarks;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.StudentMarksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class StudentMarksRepositoryTest {

    @Mock
    private StudentMarksRepository studentMarksRepository;

    private StudentMarks studentMarks1;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testFindByRegNo() throws UserNotFoundException {
        StudentMarks studentMarks1 = new StudentMarks();
        studentMarks1.setMId(1);
        StudentEnrollment student=new StudentEnrollment();
        studentMarks1.setRegno(student);
        studentMarks1.setMarks1(85);
        studentMarks1.setMarks2(80);
        studentMarks1.setMarks3(60);
        studentMarks1.setMarks4(100);

        when(studentMarksRepository.findByRegno(student)).thenReturn(Optional.of(studentMarks1));

        Optional<StudentMarks> studentMarks=studentMarksRepository.findByRegno(student);

        assertEquals(85, studentMarks.get().getMarks1());


    }

    @Test
    public void testSaveStudentMarks() {
        StudentMarks studentMarks2 = new StudentMarks();
        studentMarks2.setMId(2);
        StudentEnrollment student2=new StudentEnrollment();
        studentMarks2.setRegno(student2);
        studentMarks2.setMarks1(90);

        when(studentMarksRepository.save(any(StudentMarks.class))).thenReturn(studentMarks2);

        StudentMarks savedStudentMarks = studentMarksRepository.save(studentMarks2);

        assertEquals(2, savedStudentMarks.getMId());
        assertEquals(90, savedStudentMarks.getMarks1());
    }

    @Test
    public void testDeleteStudentMarks() {
        StudentMarks studentMarks = new StudentMarks();
        studentMarks.setMId(2);
        StudentEnrollment student2=new StudentEnrollment();
        studentMarks.setRegno(student2);
        studentMarks.setMarks1(90);

        doNothing().when(studentMarksRepository).delete(studentMarks);
        studentMarksRepository.delete(studentMarks);

        verify(studentMarksRepository, times(1)).delete(studentMarks);
    }



    @Test
    public void testFindByRegNoNotFound() {
        when(studentMarksRepository.findByRegno(any(StudentEnrollment.class))).thenReturn(Optional.empty());

        Optional<StudentMarks> foundStudentMarks = studentMarksRepository.findByRegno(any(StudentEnrollment.class));

        assertEquals(Optional.empty(), foundStudentMarks);
    }

}
