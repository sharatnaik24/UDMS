package com.project.UDMS.controller;

import com.project.UDMS.controler.StudentEnrollController;
import com.project.UDMS.dto.StudentEnrollmentDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.StudentEnrollmentService;
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

public class EnrollementTest {

    @Mock
    private StudentEnrollmentService studentEnrollmentService;

    @InjectMocks
    private StudentEnrollController studentEnrollController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        StudentEnrollmentDto createdDto = new StudentEnrollmentDto();
        when(studentEnrollmentService.create(any(StudentEnrollmentDto.class))).thenReturn(createdDto);
        ResponseEntity<StudentEnrollmentDto> response = studentEnrollController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllSuccess() {
        StudentEnrollmentDto dto1 = new StudentEnrollmentDto();
        StudentEnrollmentDto dto2 = new StudentEnrollmentDto();
        List<StudentEnrollmentDto> dtos = Arrays.asList(dto1, dto2);
        when(studentEnrollmentService.getAll()).thenReturn(dtos);
        ResponseEntity<List<StudentEnrollmentDto>> response = studentEnrollController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetByRegNoSuccess() throws UserNotFoundException {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        when(studentEnrollmentService.getByRegNo(anyString())).thenReturn(dto);
        ResponseEntity<StudentEnrollmentDto> response = studentEnrollController.getByRegNo("reg1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetByRegNoNotFound() throws UserNotFoundException {
        when(studentEnrollmentService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            studentEnrollController.getByRegNo("reg1");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        StudentEnrollmentDto updatedDto = new StudentEnrollmentDto();
        when(studentEnrollmentService.getByRegNo(anyString())).thenReturn(dto);
        when(studentEnrollmentService.create(any(StudentEnrollmentDto.class))).thenReturn(updatedDto);
        ResponseEntity<StudentEnrollmentDto> response = studentEnrollController.update(dto, "reg1");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateNotFound() throws UserNotFoundException {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        when(studentEnrollmentService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            studentEnrollController.update(dto, "reg1");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        doNothing().when(studentEnrollmentService).deleteByRegNo(anyString());
        ResponseEntity<String> response = studentEnrollController.delete("reg1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted the record with id =reg1", response.getBody());
    }

    @Test
    public void testDeleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Not Found")).when(studentEnrollmentService).deleteByRegNo(anyString());
        try {
            studentEnrollController.delete("reg1");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }
}
