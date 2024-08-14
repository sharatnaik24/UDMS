package com.project.UDMS.service;

import com.project.UDMS.dto.CourseDto;
import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.CourseDetailsRepository;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseDetailsRepository course;

    public CourseDetails dtoToEntity(CourseDto dto){
        CourseDetails entity=new CourseDetails();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public CourseDto entityToDto (CourseDetails entity){
        CourseDto dto=new CourseDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public CourseDto create(CourseDto dto){
        CourseDetails entity=course.save(dtoToEntity(dto));
        return (entityToDto(entity));
    }

    public List<CourseDto> getAll(){
        List<CourseDto> dtos=new ArrayList<>();
        for( CourseDetails entity: course.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public CourseDto getByRegNo(String coursename) throws UserNotFoundException {
        CourseDetails entity=course.findById(coursename).orElse(null);
        if(entity!=null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("course not fond");
    }

    public void deleteByRegNo(String coursename) throws UserNotFoundException {
        CourseDetails entity=course.findById(coursename).orElse(null);
        if(entity!=null)
            course.deleteById(coursename);
        else
            throw new UserNotFoundException("course not fond");
    }


}
