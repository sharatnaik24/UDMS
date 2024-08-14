package com.project.UDMS.service;

import com.project.UDMS.dto.ProfessorDto;
import com.project.UDMS.entity.ProfessorDetails;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.ProfessorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professsor;

    public ProfessorDetails dtoToEntity(ProfessorDto dto){
        ProfessorDetails entity=new ProfessorDetails();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public ProfessorDto entityToDto (ProfessorDetails entity){
        ProfessorDto dto=new ProfessorDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public ProfessorDto create(ProfessorDto dto){
        ProfessorDetails entity= professsor.save(dtoToEntity(dto));
        return (entityToDto(entity));
    }

    public List<ProfessorDto> getAll(){
        List<ProfessorDto> dtos=new ArrayList<>();
        for( ProfessorDetails entity: professsor.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public ProfessorDto getByRegNo(String pId) throws UserNotFoundException {
        ProfessorDetails entity= professsor.findById(pId).orElse(null);
        if(entity!=null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("professor not fond");
    }

    public void deleteByRegNo(String pId) throws UserNotFoundException {
        ProfessorDetails entity= professsor.findById(pId).orElse(null);
        if(entity!=null)
            professsor.deleteById(pId);
        else
            throw new UserNotFoundException("professor not fond");
    }


}
