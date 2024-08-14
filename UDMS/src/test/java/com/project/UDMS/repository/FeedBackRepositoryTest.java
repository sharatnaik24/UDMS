package com.project.UDMS.repository;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.Feedback;
import com.project.UDMS.entity.ProfessorDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedBackRepositoryTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    private Feedback feedback;
    private ProfessorDetails professor;
    private CourseDetails course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        professor = new ProfessorDetails();
        professor.setPId("S1");
        professor.setName("Dr. Smith");
        professor.setQualification("PhD");
        professor.setMail("dr.smith@example.com");
        professor.setMob("1234567890");

        course = new CourseDetails();
        course.setCourseName("Computer Science");

        feedback = new Feedback();
        feedback.setFID(1);
        feedback.setProffesor(professor);
        feedback.setCoursename(course);
        feedback.setFeedback("Great course!");
    }

    @Test
    public void testFindByIdSuccess() {
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(feedback));

        Optional<Feedback> foundFeedback = feedbackRepository.findById(1);

        assertTrue(foundFeedback.isPresent());
        assertEquals("Great course!", foundFeedback.get().getFeedback());
        assertEquals(professor, foundFeedback.get().getProffesor());
        assertEquals(course, foundFeedback.get().getCoursename());
    }

    @Test
    public void testFindByIdNotFound() {
        when(feedbackRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Feedback> foundFeedback = feedbackRepository.findById(2);

        assertFalse(foundFeedback.isPresent());
    }

    @Test
    public void testSaveFeedback() {
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback savedFeedback = feedbackRepository.save(feedback);

        assertNotNull(savedFeedback);
        assertEquals("Great course!", savedFeedback.getFeedback());
        assertEquals(professor, savedFeedback.getProffesor());
        assertEquals(course, savedFeedback.getCoursename());
    }

    @Test
    public void testDeleteFeedback() {
        doNothing().when(feedbackRepository).delete(feedback);

        feedbackRepository.delete(feedback);

        verify(feedbackRepository, times(1)).delete(feedback);
    }
}
