package com.project.UDMS.controller;

import com.project.UDMS.controler.ProfessorController;
import com.project.UDMS.dto.ProfessorDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.ProfessorService;
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

public class ProfessorControllerTest {

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController professorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetAllSuccess() {
        ProfessorDto dto1 = new ProfessorDto();
        ProfessorDto dto2 = new ProfessorDto();
        List<ProfessorDto> dtos = Arrays.asList(dto1, dto2);
        when(professorService.getAll()).thenReturn(dtos);
        ResponseEntity<List<ProfessorDto>> response = professorController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetByProfIdSuccess() throws UserNotFoundException {
        ProfessorDto dto = new ProfessorDto();
        when(professorService.getByRegNo(anyString())).thenReturn(dto);
        ResponseEntity<ProfessorDto> response = professorController.getByRegNo("S1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetByProfIdNotFound() throws UserNotFoundException {
        when(professorService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("User Not Found"));
        try {
            professorController.getByRegNo("s1");
        } catch (UserNotFoundException e) {
            assertEquals("User Not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        ProfessorDto dto = new ProfessorDto();
        ProfessorDto updatedDto = new ProfessorDto();
        when(professorService.create(any(ProfessorDto.class))).thenReturn(updatedDto);
        ResponseEntity<ProfessorDto> response = professorController.update(dto, "S1");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateNotFound() throws UserNotFoundException {
        when(professorService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("User Not Found"));
        ProfessorDto dto = new ProfessorDto();
        try {
            professorController.update(dto, "S1");
        } catch (UserNotFoundException e) {
            assertEquals("User Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        doNothing().when(professorService).deleteByRegNo(anyString());
        ResponseEntity<String> response = professorController.delete("S1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted the record with id =S1", response.getBody());
    }

    @Test
    public void testDeleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("User Not Found")).when(professorService).deleteByRegNo(anyString());
        try {
            professorController.delete("S1");
        } catch (UserNotFoundException e) {
            assertEquals("User Not Found", e.getMessage());
        }
    }
}
