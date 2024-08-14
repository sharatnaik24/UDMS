package com.project.UDMS.repository;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.Subjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseRepositoryTest {

    @Mock
    private CourseDetailsRepository courseDetailsRepository;

    private CourseDetails courseDetails;
    private Subjects subject;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        subject = new Subjects();
        subject.setSubCode("CS101");
        subject.setSubName("Introduction to Computer Science");

        List<Subjects> subjects = new ArrayList<>();
        subjects.add(subject);

        courseDetails = new CourseDetails();
        courseDetails.setCourseName("Computer Science");
        courseDetails.setSubjects(subjects);
    }

    @Test
    public void testFindByIdSuccess() {
        when(courseDetailsRepository.findById("Computer Science")).thenReturn(Optional.of(courseDetails));

        Optional<CourseDetails> foundCourse = courseDetailsRepository.findById("Computer Science");

        assertTrue(foundCourse.isPresent());
        assertEquals("Computer Science", foundCourse.get().getCourseName());
        assertEquals(1, foundCourse.get().getSubjects().size());
        assertEquals(subject.getSubCode(), foundCourse.get().getSubjects().get(0).getSubCode());
    }

    @Test
    public void testFindByIdNotFound() {
        when(courseDetailsRepository.findById("Nonexistent Course")).thenReturn(Optional.empty());

        Optional<CourseDetails> foundCourse = courseDetailsRepository.findById("Nonexistent Course");

        assertFalse(foundCourse.isPresent());
    }

    @Test
    public void testSaveCourseDetails() {
        when(courseDetailsRepository.save(any(CourseDetails.class))).thenReturn(courseDetails);

        CourseDetails savedCourse = courseDetailsRepository.save(courseDetails);

        assertNotNull(savedCourse);
        assertEquals("Computer Science", savedCourse.getCourseName());
        assertEquals(1, savedCourse.getSubjects().size());
        assertEquals(subject.getSubCode(), savedCourse.getSubjects().get(0).getSubCode());
    }

    @Test
    public void testDeleteCourseDetails() {
        doNothing().when(courseDetailsRepository).delete(courseDetails);

        courseDetailsRepository.delete(courseDetails);

        verify(courseDetailsRepository, times(1)).delete(courseDetails);
    }
}
