package com.project.UDMS.service;

import com.project.UDMS.dto.StudentEnrollmentDto;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentEnrollmentService {
    @Autowired
    private StudentEnrollmentRepository student;

    public StudentEnrollment dtoToEntity(StudentEnrollmentDto dto){
        StudentEnrollment entity=new StudentEnrollment();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public StudentEnrollmentDto entityToDto (StudentEnrollment entity){
        StudentEnrollmentDto dto=new StudentEnrollmentDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public StudentEnrollmentDto create(StudentEnrollmentDto dto){
        StudentEnrollment entity=student.save(dtoToEntity(dto));
        return (entityToDto(entity));
    }

    public List<StudentEnrollmentDto> getAll(){
        List<StudentEnrollmentDto> dtos=new ArrayList<>();
        for( StudentEnrollment entity: student.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public StudentEnrollmentDto getByRegNo(String regno) throws UserNotFoundException {
        StudentEnrollment entity=student.findById(regno).orElse(null);
        if(entity!=null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("student not fond");
    }

    public void deleteByRegNo(String regno) throws UserNotFoundException {
        StudentEnrollment entity=student.findById(regno).orElse(null);
        if(entity!=null)
            student.deleteById(regno);
        else
            throw new UserNotFoundException("student not fond");
    }


}
