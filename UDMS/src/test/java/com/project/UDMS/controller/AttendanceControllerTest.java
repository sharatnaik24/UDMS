package com.project.UDMS.controller;

import com.project.UDMS.controler.AttendanceController;
import com.project.UDMS.dto.AttendanceDto;
import com.project.UDMS.entity.Subjects;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AttendanceControllerTest {

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private AttendanceController attendanceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSuccess() {
        AttendanceDto dto1 = new AttendanceDto();
        AttendanceDto dto2 = new AttendanceDto();
        List<AttendanceDto> dtos = Arrays.asList(dto1, dto2);
        when(attendanceService.getAll()).thenReturn(dtos);
        ResponseEntity<List<AttendanceDto>> response = attendanceController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());

    }

    @Test
    public void testCreateSuccess() {
        AttendanceDto dto = new AttendanceDto();
        AttendanceDto createdDto = new AttendanceDto();
        when(attendanceService.create(any(AttendanceDto.class))).thenReturn(createdDto);
        ResponseEntity<AttendanceDto> response = attendanceController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
        assertTrue(response.getBody().equals(createdDto));
    }

    @Test
    public void testGetAttendanceByDateAndSubjectSuccess() throws ParseException, UserNotFoundException {
        AttendanceDto dto = new AttendanceDto();
        String subject = "xyz";

        when(attendanceService.getByDateAndSub(anyString(), anyString())).thenReturn(dto);

        ResponseEntity<AttendanceDto> response = attendanceController.getAttendanceByDateAndSubject("2023-07-04", subject);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetAttendanceByDateAndSubjectNotFound() throws ParseException, UserNotFoundException {
        when(attendanceService.getByDateAndSub(anyString(), anyString())).thenThrow(new UserNotFoundException("Not Found"));
        try {
            attendanceController.getAttendanceByDateAndSubject("2023-07-04", "subject");
        } catch (UserNotFoundException e) {
            assertEquals("Not Found", e.getMessage());
        }
    }

    @Test
    public void testGetRegPerSuccess() {
        when(attendanceService.attendancePerc(anyString())).thenReturn(75L);
        ResponseEntity<Long> response = attendanceController.getRegPer("REG123");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(75L, response.getBody());
    }

    @Test
    public void testGetRegPerNotFound() {
        when(attendanceService.attendancePerc(anyString())).thenReturn(0L);
        ResponseEntity<Long> response = attendanceController.getRegPer("REG123");
        assertEquals(0L, response.getBody());
    }

    @Test
    public void testDeleteAttendanceSuccess() throws UserNotFoundException {
        ResponseEntity<String> response = attendanceController.deleteAttendance(1L);
        assertEquals("deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteAttendanceNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Attendance Id not Found")).when(attendanceService).delete(1L);
        try {
            attendanceController.deleteAttendance(1L);
        } catch (UserNotFoundException e) {
            assertEquals("Attendance Id not Found", e.getMessage());
        }
    }
    @Test
    public void testUpdateAttendance() throws UserNotFoundException, ParseException {
        AttendanceDto dto = new AttendanceDto();
        dto.setAbsenteeId(1L);
        when(attendanceService.getByDateAndSub(anyString(), anyString())).thenReturn(dto);
        when(attendanceService.create(any(AttendanceDto.class))).thenReturn(dto);
        ResponseEntity<AttendanceDto> response = attendanceController.update(dto, anyString(), anyString());
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}

