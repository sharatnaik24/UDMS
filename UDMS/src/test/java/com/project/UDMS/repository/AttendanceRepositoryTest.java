package com.project.UDMS.repository;

import com.project.UDMS.entity.Attendance;
import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.Subjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AttendanceRepositoryTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    private Attendance attendance;
    private Subjects subject;
    private CourseDetails course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        subject = new Subjects();
        subject.setSubCode("CS101");
        subject.setSubName("Introduction to Computer Science");

        course = new CourseDetails();
        course.setCourseName("Computer Science");
        course.setSubjects(new ArrayList<>());

        List<String> studentsAbsent = new ArrayList<>();
        studentsAbsent.add("student1");
        studentsAbsent.add("student2");

        attendance = new Attendance();
        attendance.setAbsenteeId(1L);
        attendance.setDate(new Date());
        attendance.setCourse(course);
        attendance.setSubject(subject);
        attendance.setPresent(30);
        attendance.setAbsent(2);
        attendance.setStudentsAbsent(studentsAbsent);
    }

    @Test
    public void testCountAbsencesByStudentRegNoSuccess() {
        when(attendanceRepository.countAbsencesByStudentRegNo("student1")).thenReturn(1L);

        Long absencesCount = attendanceRepository.countAbsencesByStudentRegNo("student1");

        assertEquals(1L, absencesCount);
    }

    @Test
    public void testFindByDateAndSubjectSuccess() {
        when(attendanceRepository.findByDateAndSubject(any(Date.class), any(Subjects.class))).thenReturn(attendance);

        Attendance foundAttendance = attendanceRepository.findByDateAndSubject(new Date(), subject);

        assertNotNull(foundAttendance);
        assertEquals(attendance.getAbsenteeId(), foundAttendance.getAbsenteeId());
    }
    @Test
    public void testFindByDateAndSubjectSuccessNotFound() {
        when(attendanceRepository.findByDateAndSubject(any(Date.class), any(Subjects.class))).thenReturn(null);

        Attendance foundAttendance = attendanceRepository.findByDateAndSubject(new Date(), subject);

        assertNull(foundAttendance);

    }


    @Test
    public void testFindByIdSuccess() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance));

        Optional<Attendance> foundAttendance = attendanceRepository.findById(1L);

        assertTrue(foundAttendance.isPresent());
        assertEquals(attendance.getAbsenteeId(), foundAttendance.get().getAbsenteeId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(attendanceRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Attendance> foundAttendance = attendanceRepository.findById(2L);

        assertFalse(foundAttendance.isPresent());
    }

    @Test
    public void testSaveAttendance() {
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        assertNotNull(savedAttendance);
        assertEquals(attendance.getAbsenteeId(), savedAttendance.getAbsenteeId());
    }

    @Test
    public void testDeleteAttendance() {
        doNothing().when(attendanceRepository).delete(attendance);

        attendanceRepository.delete(attendance);

        verify(attendanceRepository, times(1)).delete(attendance);
    }
}
