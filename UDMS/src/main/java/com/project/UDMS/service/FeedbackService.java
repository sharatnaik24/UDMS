package com.project.UDMS.service;

import com.project.UDMS.dto.FeedbackDto;
import com.project.UDMS.entity.Feedback;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.FeedbackRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback dtoToEntity(FeedbackDto dto){
        Feedback entity=new Feedback();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public FeedbackDto entityToDto (Feedback entity){
        FeedbackDto dto=new FeedbackDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }


    public FeedbackDto create(FeedbackDto dto){
        Feedback entity= feedbackRepository.save(dtoToEntity(dto));
        return (entityToDto(entity));
    }

    public List<FeedbackDto> getAll(){
        List<FeedbackDto> dtos=new ArrayList<>();
        for( Feedback entity: feedbackRepository.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public FeedbackDto getByRegNo(int fId) throws UserNotFoundException {
        Feedback entity= feedbackRepository.findById(fId).orElse(null);
        if(entity!=null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("feedback not fond");
    }

    public void deleteByRegNo(int fId) throws UserNotFoundException {
        Feedback entity= feedbackRepository.findById(fId).orElse(null);
        if(entity!=null)
            feedbackRepository.deleteById(fId);
        else
            throw new UserNotFoundException("feedback not fond");
    }


}
