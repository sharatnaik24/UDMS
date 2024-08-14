package com.project.UDMS.controller;

import com.project.UDMS.controler.StudentMarksController;
import com.project.UDMS.dto.StudentMarksDto;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.StudentMarksService;
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

public class StudentMarksControllerTest {

    @Mock
    private StudentMarksService studentMarksService;

    @InjectMocks
    private StudentMarksController studentMarksController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        StudentMarksDto dto = new StudentMarksDto();
        StudentMarksDto createdDto = new StudentMarksDto();
        when(studentMarksService.create(any(StudentMarksDto.class))).thenReturn(createdDto);
        ResponseEntity<StudentMarksDto> response = studentMarksController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllSuccess() {
        StudentMarksDto dto1 = new StudentMarksDto();
        StudentMarksDto dto2 = new StudentMarksDto();
        List<StudentMarksDto> dtos = Arrays.asList(dto1, dto2);
        when(studentMarksService.getAll()).thenReturn(dtos);
        ResponseEntity<List<StudentMarksDto>> response = studentMarksController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetByRegNoSuccess() throws UserNotFoundException {
        StudentMarksDto dto = new StudentMarksDto();
        when(studentMarksService.getByRegNo(anyString())).thenReturn(dto);
        ResponseEntity<StudentMarksDto> response = studentMarksController.getByRegNo("s30");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetByRegNoNotFound() throws UserNotFoundException {
        when(studentMarksService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("User Not Found"));
        try {
            ResponseEntity<StudentMarksDto> response = studentMarksController.getByRegNo("s30");
        }

        catch (Exception e) {
            assertEquals("User Not Found",e.getMessage() );
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        StudentMarksDto dto = new StudentMarksDto();
        dto.setMId(1);
        StudentEnrollment student = new StudentEnrollment();
        student.setRollNo("s30");
        dto.setRegno(student);
        StudentMarksDto updatedDto = new StudentMarksDto();
        when(studentMarksService.create(any(StudentMarksDto.class))).thenReturn(updatedDto);
        when(studentMarksService.getByRegNo(anyString())).thenReturn(dto);
        ResponseEntity<StudentMarksDto> response = studentMarksController.update(dto, "s30");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateNotFound() throws UserNotFoundException {
        when(studentMarksService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("User Not Found"));
        StudentMarksDto dto = new StudentMarksDto();
        try {
            ResponseEntity<StudentMarksDto> response = studentMarksController.update(dto, "s30");
        }
        catch (Exception e) {
            assertEquals("User Not Found",e.getMessage() );
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        doNothing().when(studentMarksService).deleteByReg(anyString());
        ResponseEntity<String> response = studentMarksController.delete("s30");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted the record with id =s30", response.getBody());
    }

    @Test
    public void testDeleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("User Not Found")).when(studentMarksService).deleteByReg(anyString());
        try {
            ResponseEntity<String> response = studentMarksController.delete("s30");
        }
        catch (Exception e) {
            assertEquals("User Not Found",e.getMessage() );
        }
    }
}
