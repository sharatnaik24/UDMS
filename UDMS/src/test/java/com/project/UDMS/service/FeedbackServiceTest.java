package com.project.UDMS.service;

import com.project.UDMS.dto.FeedbackDto;
import com.project.UDMS.entity.Feedback;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDtoToEntity() {
        FeedbackDto dto = new FeedbackDto();
        dto.setFID(1);
        dto.setFeedback("Great course!");

        Feedback entity = feedbackService.dtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getFID(), entity.getFID());
        assertEquals(dto.getFeedback(), entity.getFeedback());
    }

    @Test
    public void testEntityToDto() {
        Feedback entity = new Feedback();
        entity.setFID(1);
        entity.setFeedback("Great course!");

        FeedbackDto dto = feedbackService.entityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getFID(), dto.getFID());
        assertEquals(entity.getFeedback(), dto.getFeedback());
    }

    @Test
    public void testCreate() {
        FeedbackDto dto = new FeedbackDto();
        dto.setFeedback("Great course!");

        Feedback savedEntity = new Feedback();
        BeanUtils.copyProperties(dto, savedEntity);
        savedEntity.setFID(1);

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(savedEntity);

        FeedbackDto createdDto = feedbackService.create(dto);

        assertNotNull(createdDto);
        assertEquals(savedEntity.getFID(), createdDto.getFID());
        assertEquals(savedEntity.getFeedback(), createdDto.getFeedback());
    }

    @Test
    public void testGetAll() {
        List<Feedback> entities = new ArrayList<>();
        Feedback entity1 = new Feedback();
        entity1.setFID(1);
        entity1.setFeedback("Great course!");
        entities.add(entity1);

        Feedback entity2 = new Feedback();
        entity2.setFID(2);
        entity2.setFeedback("Needs improvement.");
        entities.add(entity2);

        when(feedbackRepository.findAll()).thenReturn(entities);

        List<FeedbackDto> dtos = feedbackService.getAll();

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entity1.getFID(), dtos.get(0).getFID());
        assertEquals(entity1.getFeedback(), dtos.get(0).getFeedback());
        assertEquals(entity2.getFID(), dtos.get(1).getFID());
        assertEquals(entity2.getFeedback(), dtos.get(1).getFeedback());
    }

    @Test
    public void testGetByF_Id_Valid() throws UserNotFoundException {
        int fId = 1;
        Feedback entity = new Feedback();
        entity.setFID(fId);
        entity.setFeedback("Great course!");

        when(feedbackRepository.findById(fId)).thenReturn(Optional.of(entity));

        FeedbackDto dto = feedbackService.getByRegNo(fId);

        assertNotNull(dto);
        assertEquals(entity.getFID(), dto.getFID());
        assertEquals(entity.getFeedback(), dto.getFeedback());
    }

    @Test
    public void testGetByF_Id_Invalid() {
        int fId = 1;

        when(feedbackRepository.findById(fId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            feedbackService.getByRegNo(fId);
        });
    }

    @Test
    public void testDeleteByF_Id_Valid() throws UserNotFoundException {
        int fId = 1;
        Feedback entity = new Feedback();
        entity.setFID(fId);

        when(feedbackRepository.findById(fId)).thenReturn(Optional.of(entity));

        feedbackService.deleteByRegNo(fId);

        verify(feedbackRepository, times(1)).deleteById(fId);
    }

    @Test
    public void testDeleteByF_Id_Invalid() {
        int fId = 1;

        when(feedbackRepository.findById(fId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            feedbackService.deleteByRegNo(fId);
        });
    }
}
