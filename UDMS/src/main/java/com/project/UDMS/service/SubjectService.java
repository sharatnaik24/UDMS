package com.project.UDMS.service;

import com.project.UDMS.dto.SubjectsDto;
import com.project.UDMS.entity.Subjects;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.StudentEnrollmentRepository;
import com.project.UDMS.repository.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subject;

    public Subjects dtoToEntity(SubjectsDto dto){
        Subjects entity=new Subjects();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public SubjectsDto entityToDto (Subjects entity){
        SubjectsDto dto=new SubjectsDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public SubjectsDto create(SubjectsDto dto){
        Subjects entity=subject.save(dtoToEntity(dto));
        return (entityToDto(entity));
    }

    public List<SubjectsDto> getAll(){
        List<SubjectsDto> dtos=new ArrayList<>();
        for( Subjects entity: subject.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public SubjectsDto getBySubCode(String subCode) throws UserNotFoundException {
        Subjects entity=subject.findById(subCode).orElse(null);
        if(entity!=null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("subject not fond");
    }

    public void deleteBySubCode(String subCode) throws UserNotFoundException {
        Subjects entity=subject.findById(subCode).orElse(null);
        if(entity!=null)
            subject.deleteById(subCode);
        else
            throw new UserNotFoundException("subject not fond");
    }


}
