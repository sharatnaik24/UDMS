package com.project.UDMS.service;

import com.project.UDMS.dto.ProfessorDto;
import com.project.UDMS.entity.ProfessorDetails;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.ProfessorRepository;
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

public class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorService professorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDtoToEntity() {
        ProfessorDto dto = new ProfessorDto();
        dto.setPId("P1");
        dto.setName("Dr. John Doe");
        dto.setQualification("PhD");
        dto.setMail("john.doe@example.com");
        dto.setMob("1234567890");

        ProfessorDetails entity = professorService.dtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getPId(), entity.getPId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getQualification(), entity.getQualification());
        assertEquals(dto.getMail(), entity.getMail());
        assertEquals(dto.getMob(), entity.getMob());
    }

    @Test
    public void testEntityToDto() {
        ProfessorDetails entity = new ProfessorDetails();
        entity.setPId("P1");
        entity.setName("Dr. John Doe");
        entity.setQualification("PhD");
        entity.setMail("john.doe@example.com");
        entity.setMob("1234567890");

        ProfessorDto dto = professorService.entityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getPId(), dto.getPId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getQualification(), dto.getQualification());
        assertEquals(entity.getMail(), dto.getMail());
        assertEquals(entity.getMob(), dto.getMob());
    }

    @Test
    public void testCreate() {
        ProfessorDto dto = new ProfessorDto();
        dto.setName("Dr. John Doe");
        dto.setQualification("PhD");
        dto.setMail("john.doe@example.com");
        dto.setMob("1234567890");

        ProfessorDetails savedEntity = new ProfessorDetails();
        BeanUtils.copyProperties(dto, savedEntity);
        savedEntity.setPId("S1");

        when(professorRepository.save(any(ProfessorDetails.class))).thenReturn(savedEntity);

        ProfessorDto createdDto = professorService.create(dto);

        assertNotNull(createdDto);
        assertEquals(savedEntity.getPId(), createdDto.getPId());
        assertEquals(savedEntity.getName(), createdDto.getName());
        assertEquals(savedEntity.getQualification(), createdDto.getQualification());
        assertEquals(savedEntity.getMail(), createdDto.getMail());
        assertEquals(savedEntity.getMob(), createdDto.getMob());
    }

    @Test
    public void testGetAll() {
        List<ProfessorDetails> entities = new ArrayList<>();
        ProfessorDetails entity1 = new ProfessorDetails();
        entity1.setPId("S1");
        entity1.setName("Dr. John Doe");
        entity1.setQualification("PhD");
        entity1.setMail("john.doe@example.com");
        entity1.setMob("1234567890");
        entities.add(entity1);

        ProfessorDetails entity2 = new ProfessorDetails();
        entity2.setPId("P1");
        entity2.setName("Dr. Jane Smith");
        entity2.setQualification("PhD");
        entity2.setMail("jane.smith@example.com");
        entity2.setMob("0987654321");
        entities.add(entity2);

        when(professorRepository.findAll()).thenReturn(entities);

        List<ProfessorDto> dtos = professorService.getAll();

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entity1.getPId(), dtos.get(0).getPId());
        assertEquals(entity1.getName(), dtos.get(0).getName());
        assertEquals(entity1.getQualification(), dtos.get(0).getQualification());
        assertEquals(entity1.getMail(), dtos.get(0).getMail());
        assertEquals(entity1.getMob(), dtos.get(0).getMob());
        assertEquals(entity2.getPId(), dtos.get(1).getPId());
        assertEquals(entity2.getName(), dtos.get(1).getName());
        assertEquals(entity2.getQualification(), dtos.get(1).getQualification());
        assertEquals(entity2.getMail(), dtos.get(1).getMail());
        assertEquals(entity2.getMob(), dtos.get(1).getMob());
    }

    @Test
    public void testGetByProfId_Valid() throws UserNotFoundException {
        String pId = "P1";
        ProfessorDetails entity = new ProfessorDetails();
        entity.setPId(pId);
        entity.setName("Dr. John Doe");
        entity.setQualification("PhD");
        entity.setMail("john.doe@example.com");
        entity.setMob("1234567890");

        when(professorRepository.findById(pId)).thenReturn(Optional.of(entity));

        ProfessorDto dto = professorService.getByRegNo(pId);

        assertNotNull(dto);
        assertEquals(entity.getPId(), dto.getPId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getQualification(), dto.getQualification());
        assertEquals(entity.getMail(), dto.getMail());
        assertEquals(entity.getMob(), dto.getMob());
    }

    @Test
    public void testGetByProfId_Invalid() {
        String pId = "P1";

        when(professorRepository.findById(pId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            professorService.getByRegNo(pId);
        });
    }

    @Test
    public void testDeleteByProfId_Valid() throws UserNotFoundException {
        String pId = "P1";
        ProfessorDetails entity = new ProfessorDetails();
        entity.setPId(pId);

        when(professorRepository.findById(pId)).thenReturn(Optional.of(entity));

        professorService.deleteByRegNo(pId);

        verify(professorRepository, times(1)).deleteById(pId);
    }

    @Test
    public void testDeleteByProfId_Invalid() {
        String pId = "P1";

        when(professorRepository.findById(pId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            professorService.deleteByRegNo(pId);
        });
    }
}
