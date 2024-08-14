package com.project.UDMS.service;

import com.project.UDMS.dto.CourseDto;
import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.CourseDetailsRepository;
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

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseDetailsRepository courseDetailsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDtoToEntity() {
        CourseDto dto = new CourseDto();
        dto.setCourseName("CS101");

        CourseDetails entity = courseService.dtoToEntity(dto);

        assertEquals(dto.getCourseName(), entity.getCourseName());
    }

    @Test
    public void testEntityToDto() {
        CourseDetails entity = new CourseDetails();
        entity.setCourseName("CS101");

        CourseDto dto = courseService.entityToDto(entity);

        assertEquals(entity.getCourseName(), dto.getCourseName());
    }

    @Test
    public void testCreate() {
        CourseDto dto = new CourseDto();
        dto.setCourseName("CS101");

        CourseDetails entity = courseService.dtoToEntity(dto);

        when(courseDetailsRepository.save(any(CourseDetails.class))).thenReturn(entity);

        CourseDto result = courseService.create(dto);

        assertNotNull(result);
        assertEquals(dto.getCourseName(), result.getCourseName());
    }

    @Test
    public void testGetAll() {
        List<CourseDetails> entities = new ArrayList<>();
        CourseDetails entity1 = new CourseDetails();
        entity1.setCourseName("CS101");
        entities.add(entity1);
        CourseDetails entity2 = new CourseDetails();
        entity2.setCourseName("CS102");
        entities.add(entity2);

        when(courseDetailsRepository.findAll()).thenReturn(entities);

        List<CourseDto> result = courseService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetByCourseName() throws UserNotFoundException {
        String courseName = "CS101";
        CourseDetails entity = new CourseDetails();
        entity.setCourseName(courseName);

        when(courseDetailsRepository.findById(courseName)).thenReturn(Optional.of(entity));

        CourseDto result = courseService.getByRegNo(courseName);

        assertNotNull(result);
        assertEquals(courseName, result.getCourseName());
    }

    @Test
    public void testGetByCourseName_NotFound() {
        String courseName = "CS101";

        when(courseDetailsRepository.findById(courseName)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            courseService.getByRegNo(courseName);
        });
    }

    @Test
    public void testDeleteByCourseName() throws UserNotFoundException {
        String courseName = "CS101";
        CourseDetails entity = new CourseDetails();
        entity.setCourseName(courseName);

        when(courseDetailsRepository.findById(courseName)).thenReturn(Optional.of(entity));
        doNothing().when(courseDetailsRepository).deleteById(courseName);

        courseService.deleteByRegNo(courseName);

        verify(courseDetailsRepository, times(1)).deleteById(courseName);
    }

    @Test
    public void testDeleteByCourseName_NotFound() {
        String courseName = "CS101";

        when(courseDetailsRepository.findById(courseName)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            courseService.deleteByRegNo(courseName);
        });
    }
}
