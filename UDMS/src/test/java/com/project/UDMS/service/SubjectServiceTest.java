package com.project.UDMS.service;

import com.project.UDMS.dto.SubjectsDto;
import com.project.UDMS.entity.Subjects;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDtoToEntity() {
        SubjectsDto dto = new SubjectsDto();
        dto.setSubCode("CS101");
        dto.setSubName("Computer Science");
        dto.setSubType('T'); // Assuming 'T' stands for Theory

        Subjects entity = subjectService.dtoToEntity(dto);

        assertEquals(dto.getSubCode(), entity.getSubCode());
        assertEquals(dto.getSubName(), entity.getSubName());
        assertEquals(dto.getSubType(), entity.getSubType());
    }

    @Test
    public void testEntityToDto() {
        Subjects entity = new Subjects();
        entity.setSubCode("CS101");
        entity.setSubName("Computer Science");
        entity.setSubType('T');

        SubjectsDto dto = subjectService.entityToDto(entity);

        assertEquals(entity.getSubCode(), dto.getSubCode());
        assertEquals(entity.getSubName(), dto.getSubName());
        assertEquals(entity.getSubType(), dto.getSubType());
    }

    @Test
    public void testCreate() {
        SubjectsDto dto = new SubjectsDto();
        dto.setSubCode("CS101");
        dto.setSubName("Computer Science");
        dto.setSubType('T');

        Subjects entity = new Subjects();
        entity.setSubCode(dto.getSubCode());
        entity.setSubName(dto.getSubName());
        entity.setSubType(dto.getSubType());

        when(subjectRepository.save(any(Subjects.class))).thenReturn(entity);

        SubjectsDto createdDto = subjectService.create(dto);

        assertEquals(dto.getSubCode(), createdDto.getSubCode());
        assertEquals(dto.getSubName(), createdDto.getSubName());
        assertEquals(dto.getSubType(), createdDto.getSubType());
    }

    @Test
    public void testGetAll() {
        List<Subjects> entities = new ArrayList<>();
        Subjects entity1 = new Subjects();
        entity1.setSubCode("CS101");
        entity1.setSubName("Computer Science");
        entity1.setSubType('T');
        entities.add(entity1);

        Subjects entity2 = new Subjects();
        entity2.setSubCode("MA101");
        entity2.setSubName("Mathematics");
        entity2.setSubType('T');
        entities.add(entity2);

        when(subjectRepository.findAll()).thenReturn(entities);

        List<SubjectsDto> dtos = subjectService.getAll();

        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getSubCode(), dtos.get(0).getSubCode());
        assertEquals(entities.get(1).getSubCode(), dtos.get(1).getSubCode());
    }

    @Test
    public void testGetBySubCode_Success() throws UserNotFoundException {
        String subCode = "CS101";
        Subjects entity = new Subjects();
        entity.setSubCode(subCode);
        entity.setSubName("Computer Science");
        entity.setSubType('T');

        when(subjectRepository.findById(subCode)).thenReturn(Optional.of(entity));

        SubjectsDto dto = subjectService.getBySubCode(subCode);

        assertNotNull(dto);
        assertEquals(entity.getSubCode(), dto.getSubCode());
        assertEquals(entity.getSubName(), dto.getSubName());
        assertEquals(entity.getSubType(), dto.getSubType());
    }

    @Test
    public void testGetBySubCode_NotFound() {
        String subCode = "CS101";

        when(subjectRepository.findById(subCode)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> subjectService.getBySubCode(subCode));
    }

    @Test
    public void testDeleteBySubCode_Success() throws UserNotFoundException {
        String subCode = "CS101";
        Subjects entity = new Subjects();
        entity.setSubCode(subCode);
        entity.setSubName("Computer Science");
        entity.setSubType('T');

        when(subjectRepository.findById(subCode)).thenReturn(Optional.of(entity));

        subjectService.deleteBySubCode(subCode);

        verify(subjectRepository, times(1)).deleteById(subCode);
    }

    @Test
    public void testDeleteBySubCode_NotFound() {
        String subCode = "CS101";

        when(subjectRepository.findById(subCode)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> subjectService.deleteBySubCode(subCode));
        verify(subjectRepository, never()).deleteById(subCode);
    }
}
