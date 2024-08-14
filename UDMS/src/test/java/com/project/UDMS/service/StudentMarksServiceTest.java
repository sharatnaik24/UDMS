package com.project.UDMS.service;

import com.project.UDMS.dto.StudentMarksDto;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.StudentMarks;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import com.project.UDMS.repository.StudentMarksRepository;
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

public class StudentMarksServiceTest {

    @Mock
    private StudentMarksRepository studentMarksRepository;

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @InjectMocks
    private StudentMarksService studentMarksService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDtoToEntity() {
        StudentMarksDto dto = new StudentMarksDto();
        dto.setMId(1);
        dto.setMarks1(80);
        dto.setMarks2(75);
        dto.setMarks3(85);
        dto.setMarks4(90);

        StudentMarks entity = studentMarksService.dtoToEntity(dto);

        assertEquals(dto.getMId(), entity.getMId());
        assertEquals(dto.getMarks1(), entity.getMarks1());
        assertEquals(dto.getMarks2(), entity.getMarks2());
        assertEquals(dto.getMarks3(), entity.getMarks3());
        assertEquals(dto.getMarks4(), entity.getMarks4());
    }

    @Test
    public void testEntityToDto() {
        StudentMarks entity = new StudentMarks();
        entity.setMId(1);
        entity.setMarks1(80);
        entity.setMarks2(75);
        entity.setMarks3(85);
        entity.setMarks4(90);

        StudentMarksDto dto = studentMarksService.entityToDto(entity);

        assertEquals(entity.getMId(), dto.getMId());
        assertEquals(entity.getMarks1(), dto.getMarks1());
        assertEquals(entity.getMarks2(), dto.getMarks2());
        assertEquals(entity.getMarks3(), dto.getMarks3());
        assertEquals(entity.getMarks4(), dto.getMarks4());
    }

    @Test
    public void testCreate() {
        StudentMarksDto dto = new StudentMarksDto();
        dto.setMId(1);
        StudentEnrollment student = new StudentEnrollment();
        student.setRollNo("12345");
        dto.setRegno(student);
        dto.setMarks1(80);
        dto.setMarks2(75);
        dto.setMarks3(85);
        dto.setMarks4(90);

        StudentMarks entity = new StudentMarks();
        BeanUtils.copyProperties(dto, entity);

        when(studentEnrollmentRepository.existsById(anyString())).thenReturn(false);
        when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(student);
        when(studentMarksRepository.save(any(StudentMarks.class))).thenReturn(entity);

        StudentMarksDto createdDto = studentMarksService.create(dto);

        assertEquals(dto.getMId(), createdDto.getMId());
        assertEquals(dto.getMarks1(), createdDto.getMarks1());
        assertEquals(dto.getMarks2(), createdDto.getMarks2());
        assertEquals(dto.getMarks3(), createdDto.getMarks3());
        assertEquals(dto.getMarks4(), createdDto.getMarks4());
    }

    @Test
    public void testGetAll() {
        List<StudentMarks> entities = new ArrayList<>();
        StudentMarks entity1 = new StudentMarks();
        entity1.setMId(1);
        entity1.setMarks1(80);
        entity1.setMarks2(75);
        entity1.setMarks3(85);
        entity1.setMarks4(90);
        entities.add(entity1);

        StudentMarks entity2 = new StudentMarks();
        entity2.setMId(2);
        entity2.setMarks1(85);
        entity2.setMarks2(78);
        entity2.setMarks3(88);
        entity2.setMarks4(92);
        entities.add(entity2);

        when(studentMarksRepository.findAll()).thenReturn(entities);

        List<StudentMarksDto> dtos = studentMarksService.getAll();

        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getMId(), dtos.get(0).getMId());
        assertEquals(entities.get(1).getMId(), dtos.get(1).getMId());
    }

    @Test
    public void testGetByRegNo_Success() throws UserNotFoundException {
        String reg = "s30";
        StudentEnrollment student = new StudentEnrollment();
        student.setRollNo(reg);

        StudentMarks entity = new StudentMarks();
        entity.setMId(1);
        entity.setRegno(student);
        entity.setMarks1(80);
        entity.setMarks2(75);
        entity.setMarks3(85);
        entity.setMarks4(90);

        when(studentEnrollmentRepository.findById(reg)).thenReturn(Optional.of(student));
        when(studentMarksRepository.findByRegno(student)).thenReturn(Optional.of(entity));

        StudentMarksDto dto = studentMarksService.getByRegNo(reg);

        assertNotNull(dto);
        assertEquals(entity.getMId(), dto.getMId());
        assertEquals(entity.getRegno(), dto.getRegno());
        assertEquals(entity.getMarks1(), dto.getMarks1());
        assertEquals(entity.getMarks2(), dto.getMarks2());
        assertEquals(entity.getMarks3(), dto.getMarks3());
        assertEquals(entity.getMarks4(), dto.getMarks4());
    }

    @Test
    public void testGetByRegNo_NotFound() {
        String reg = "s30";

        when(studentEnrollmentRepository.findById(reg)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> studentMarksService.getByRegNo(reg));
    }

    @Test
    public void testDeleteByReg_Success() throws UserNotFoundException {
        String reg = "s30";
        StudentEnrollment student = new StudentEnrollment();
        student.setRollNo(reg);

        StudentMarks entity = new StudentMarks();
        entity.setMId(1);
        entity.setRegno(student);
        entity.setMarks1(80);
        entity.setMarks2(75);
        entity.setMarks3(85);
        entity.setMarks4(90);

        when(studentEnrollmentRepository.findById(reg)).thenReturn(Optional.of(student));
        when(studentMarksRepository.findByRegno(student)).thenReturn(Optional.of(entity));

        studentMarksService.deleteByReg(reg);

        verify(studentMarksRepository, times(1)).deleteById(entity.getMId());
    }

    @Test
    public void testDeleteByReg_NotFound() {
        String reg = "s30";

        when(studentEnrollmentRepository.findById(reg)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> studentMarksService.deleteByReg(reg));
        verify(studentMarksRepository, never()).deleteById(anyInt());
    }
}
