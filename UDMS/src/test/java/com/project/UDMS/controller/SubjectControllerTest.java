package com.project.UDMS.controller;

import com.project.UDMS.controler.SubjectController;
import com.project.UDMS.dto.SubjectsDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.SubjectService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        SubjectsDto dto = new SubjectsDto();
        SubjectsDto createdDto = new SubjectsDto();
        when(subjectService.create(any(SubjectsDto.class))).thenReturn(createdDto);
        ResponseEntity<SubjectsDto> response = subjectController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllSuccess() {
        SubjectsDto dto1 = new SubjectsDto();
        SubjectsDto dto2 = new SubjectsDto();
        List<SubjectsDto> dtos = Arrays.asList(dto1, dto2);
        when(subjectService.getAll()).thenReturn(dtos);
        ResponseEntity<List<SubjectsDto>> response = subjectController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetBySubCodeSuccess() throws UserNotFoundException {
        SubjectsDto dto = new SubjectsDto();
        when(subjectService.getBySubCode(anyString())).thenReturn(dto);
        ResponseEntity<SubjectsDto> response = subjectController.getBySubCode("subcode");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetBySubCodeNotFound() throws UserNotFoundException {
        when(subjectService.getBySubCode(anyString())).thenThrow(new UserNotFoundException("Subject Not Found"));
        try {
            subjectController.getBySubCode("nonexistentcode");
        } catch (UserNotFoundException e) {
            assertEquals("Subject Not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        SubjectsDto dto = new SubjectsDto();
        dto.setSubCode("subcode");
        when(subjectService.create(any(SubjectsDto.class))).thenReturn(dto);
        ResponseEntity<SubjectsDto> response = subjectController.update(dto, "subcode");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testUpdateSubjectNotFound() throws UserNotFoundException {
        SubjectsDto dto = new SubjectsDto();
        dto.setSubCode("subcode");
        when(subjectService.getBySubCode(anyString())).thenThrow(new UserNotFoundException("Subject Not Found"));
        try {
            subjectController.update(dto, "nonexistentcode");
        } catch (UserNotFoundException e) {
            assertEquals("Subject Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        ResponseEntity<String> response = subjectController.delete("subcode");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted the record with id =subcode", response.getBody());
        verify(subjectService, times(1)).deleteBySubCode("subcode");
    }

    @Test
    public void testDeleteSubjectNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Subject Not Found")).when(subjectService).deleteBySubCode(anyString());
        try {
            subjectController.delete("nonexistentcode");
        } catch (UserNotFoundException e) {
            assertEquals("Subject Not Found", e.getMessage());
        }
    }

}
