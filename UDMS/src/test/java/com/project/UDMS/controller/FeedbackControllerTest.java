package com.project.UDMS.controller;

import com.project.UDMS.controler.FeedbackController;
import com.project.UDMS.dto.FeedbackDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        FeedbackDto dto = new FeedbackDto();
        FeedbackDto createdDto = new FeedbackDto();

        when(feedbackService.create(any(FeedbackDto.class))).thenReturn(createdDto);

        ResponseEntity<FeedbackDto> response = feedbackController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllSuccess() {
        FeedbackDto dto1 = new FeedbackDto();
        FeedbackDto dto2 = new FeedbackDto();
        List<FeedbackDto> dtos = Arrays.asList(dto1, dto2);
        when(feedbackService.getAll()).thenReturn(dtos);
        ResponseEntity<List<FeedbackDto>> response = feedbackController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetByRegNoSuccess() throws UserNotFoundException {
        FeedbackDto dto = new FeedbackDto();
        when(feedbackService.getByRegNo(anyInt())).thenReturn(dto);
        ResponseEntity<FeedbackDto> response = feedbackController.getByRegNo(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetByRegNoNotFound() throws UserNotFoundException {
        when(feedbackService.getByRegNo(anyInt())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            feedbackController.getByRegNo(1);
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        FeedbackDto dto = new FeedbackDto();
        FeedbackDto updatedDto = new FeedbackDto();
        when(feedbackService.getByRegNo(anyInt())).thenReturn(dto);
        when(feedbackService.create(any(FeedbackDto.class))).thenReturn(updatedDto);
        ResponseEntity<FeedbackDto> response = feedbackController.update(dto, 1);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateNotFound() throws UserNotFoundException {
        FeedbackDto dto = new FeedbackDto();
        when(feedbackService.getByRegNo(anyInt())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            feedbackController.update(dto, 1);
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        doNothing().when(feedbackService).deleteByRegNo(anyInt());
        ResponseEntity<String> response = feedbackController.delete(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted the record with id =1", response.getBody());
    }

    @Test
    public void testDeleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Not Found")).when(feedbackService).deleteByRegNo(anyInt());
        try {
            feedbackController.delete(1);
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }
}
