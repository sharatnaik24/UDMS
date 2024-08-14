package com.project.UDMS.service;

import com.project.UDMS.dto.StudentEnrollmentDto;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.StudentEnrollmentRepository;
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

public class StudentEnrollmentServiceTest {

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @InjectMocks
    private StudentEnrollmentService studentEnrollmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDtoToEntity() {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        dto.setRollNo("S001");
        dto.setName("Alice");
        dto.setMail("alice@example.com");
        dto.setMob("9876543210");

        StudentEnrollment entity = studentEnrollmentService.dtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getRollNo(), entity.getRollNo());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getMail(), entity.getMail());
        assertEquals(dto.getMob(), entity.getMob());
    }

    @Test
    public void testEntityToDto() {
        StudentEnrollment entity = new StudentEnrollment();
        entity.setRollNo("S001");
        entity.setName("Alice");
        entity.setMail("alice@example.com");
        entity.setMob("9876543210");

        StudentEnrollmentDto dto = studentEnrollmentService.entityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getRollNo(), dto.getRollNo());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getMail(), dto.getMail());
        assertEquals(entity.getMob(), dto.getMob());
    }

    @Test
    public void testCreate() {
        StudentEnrollmentDto dto = new StudentEnrollmentDto();
        dto.setRollNo("S001");
        dto.setName("Alice");
        dto.setMail("alice@example.com");
        dto.setMob("9876543210");
        StudentEnrollment savedEntity = new StudentEnrollment();
        BeanUtils.copyProperties(dto, savedEntity);
        when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(savedEntity);
        StudentEnrollmentDto createdDto = studentEnrollmentService.create(dto);
        assertNotNull(createdDto);
        assertEquals(savedEntity.getRollNo(), createdDto.getRollNo());
        assertEquals(savedEntity.getName(), createdDto.getName());
        assertEquals(savedEntity.getMail(), createdDto.getMail());
        assertEquals(savedEntity.getMob(), createdDto.getMob());
    }

    @Test
    public void testGetAll() {
        List<StudentEnrollment> entities = new ArrayList<>();
        StudentEnrollment entity1 = new StudentEnrollment();
        entity1.setRollNo("S001");
        entity1.setName("Alice");
        entity1.setMail("alice@example.com");
        entity1.setMob("9876543210");
        entities.add(entity1);
        StudentEnrollment entity2 = new StudentEnrollment();
        entity2.setRollNo("S002");
        entity2.setName("Bob");
        entity2.setMail("bob@example.com");
        entity2.setMob("9876543211");
        entities.add(entity2);
        when(studentEnrollmentRepository.findAll()).thenReturn(entities);
        List<StudentEnrollmentDto> dtos = studentEnrollmentService.getAll();
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entity1.getRollNo(), dtos.get(0).getRollNo());
        assertEquals(entity1.getName(), dtos.get(0).getName());
        assertEquals(entity1.getMail(), dtos.get(0).getMail());
        assertEquals(entity1.getMob(), dtos.get(0).getMob());
        assertEquals(entity2.getRollNo(), dtos.get(1).getRollNo());
        assertEquals(entity2.getName(), dtos.get(1).getName());
        assertEquals(entity2.getMail(), dtos.get(1).getMail());
        assertEquals(entity2.getMob(), dtos.get(1).getMob());
    }

    @Test
    public void testGetByRegNo_Valid() throws UserNotFoundException {
        String regNo = "S001";
        StudentEnrollment entity = new StudentEnrollment();
        entity.setRollNo(regNo);
        entity.setName("Alice");
        entity.setMail("alice@example.com");
        entity.setMob("9876543210");
        when(studentEnrollmentRepository.findById(regNo)).thenReturn(Optional.of(entity));
        StudentEnrollmentDto dto = studentEnrollmentService.getByRegNo(regNo);
        assertNotNull(dto);
        assertEquals(entity.getRollNo(), dto.getRollNo());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getMail(), dto.getMail());
        assertEquals(entity.getMob(), dto.getMob());
    }

    @Test
    public void testGetByRegNo_Invalid() {
        String regNo = "S001";
        when(studentEnrollmentRepository.findById(regNo)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            studentEnrollmentService.getByRegNo(regNo);
        });
    }

    @Test
    public void testDeleteByRegNo_Valid() throws UserNotFoundException {
        String regNo = "S001";
        StudentEnrollment entity = new StudentEnrollment();
        entity.setRollNo(regNo);
        when(studentEnrollmentRepository.findById(regNo)).thenReturn(Optional.of(entity));
        studentEnrollmentService.deleteByRegNo(regNo);
        verify(studentEnrollmentRepository, times(1)).deleteById(regNo);
    }

    @Test
    public void testDeleteByRegNo_Invalid() {
        String regNo = "S001";
        when(studentEnrollmentRepository.findById(regNo)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            studentEnrollmentService.deleteByRegNo(regNo);
        });
    }
}
