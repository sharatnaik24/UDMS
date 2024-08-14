package com.project.UDMS.service;

import com.project.UDMS.dto.StudentMarksDto;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.StudentMarks;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import com.project.UDMS.repository.StudentMarksRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentMarksService {
    @Autowired
    private StudentMarksRepository studentMarksRepository;

    @Autowired
    private StudentEnrollmentRepository studentEnrollmentRepository;

    public StudentMarks dtoToEntity(StudentMarksDto dto){
        StudentMarks entity=new StudentMarks();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public StudentMarksDto entityToDto (StudentMarks entity){
        StudentMarksDto dto=new StudentMarksDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public StudentMarksDto create(StudentMarksDto dto){
        StudentEnrollment entity= dto.getRegno();
        if(!studentEnrollmentRepository.existsById(entity.getRollNo()))
            studentEnrollmentRepository.save(entity);
        StudentMarks marks=studentMarksRepository.save(dtoToEntity(dto));
        return (entityToDto(marks));
    }

    public List<StudentMarksDto> getAll(){
        List<StudentMarksDto> dtos=new ArrayList<>();
        for( StudentMarks entity: studentMarksRepository.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public StudentMarksDto getByRegNo(String reg) throws UserNotFoundException {
        StudentEnrollment student=studentEnrollmentRepository.findById(reg).orElse(null);
        if(student!=null) {
            StudentMarks entity = studentMarksRepository.findByRegno(student).orElse(null);
            if (entity != null)
                return entityToDto(entity);
            else
                throw new UserNotFoundException("Reg Number not fond");
        }
        else
            throw new UserNotFoundException("Reg Number not fond");
    }

    public void deleteByReg(String reg) throws UserNotFoundException {
        StudentEnrollment student=studentEnrollmentRepository.findById(reg).orElse(null);
        if(student!=null) {
            StudentMarks entity = studentMarksRepository.findByRegno(student).orElse(null);

            if (entity != null)
                studentMarksRepository.deleteById(entity.getMId());
            else throw new UserNotFoundException("regNumber not fond");
        }
        else
            throw new UserNotFoundException("regNumber not fond");
    }




}
