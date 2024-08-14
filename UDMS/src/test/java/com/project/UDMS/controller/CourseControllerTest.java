package com.project.UDMS.controller;

import com.project.UDMS.controler.CourseController;
import com.project.UDMS.dto.CourseDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.CourseService;
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

public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        CourseDto dto = new CourseDto();
        CourseDto createdDto = new CourseDto();
        when(courseService.create(any(CourseDto.class))).thenReturn(createdDto);
        ResponseEntity<CourseDto> response = courseController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllSuccess() {
        CourseDto dto1 = new CourseDto();
        CourseDto dto2 = new CourseDto();
        List<CourseDto> dtos = Arrays.asList(dto1, dto2);
        when(courseService.getAll()).thenReturn(dtos);
        ResponseEntity<List<CourseDto>> response = courseController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetByCourseNameSuccess() throws UserNotFoundException {
        CourseDto dto = new CourseDto();
        when(courseService.getByRegNo(anyString())).thenReturn(dto);
        ResponseEntity<CourseDto> response = courseController.getByRegNo("course1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetByCourseNameNotFound() throws UserNotFoundException {
        when(courseService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            courseController.getByRegNo("course1");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        CourseDto dto = new CourseDto();
        CourseDto updatedDto = new CourseDto();
        when(courseService.getByRegNo(anyString())).thenReturn(dto);
        when(courseService.create(any(CourseDto.class))).thenReturn(updatedDto);
        ResponseEntity<CourseDto> response = courseController.update(dto, "course1");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    public void testUpdateNotFound() throws UserNotFoundException {
        CourseDto dto = new CourseDto();
        when(courseService.getByRegNo(anyString())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            courseController.update(dto, "course1");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        doNothing().when(courseService).deleteByRegNo(anyString());
        ResponseEntity<String> response = courseController.delete("course1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted the record with name =course1", response.getBody());
    }

    @Test
    public void testDeleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Not Found")).when(courseService).deleteByRegNo(anyString());
        try {
            courseController.delete("course1");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }
}
