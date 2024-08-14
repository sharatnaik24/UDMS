package com.project.UDMS.service;

import com.project.UDMS.dto.AttendanceDto;
import com.project.UDMS.entity.Attendance;
import com.project.UDMS.entity.Subjects;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.repository.AttendanceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private SubjectService subjectService;

    public AttendanceDto entityToDto(Attendance entity){
        AttendanceDto dto=new AttendanceDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public Attendance dtoToEntity(AttendanceDto dto){
        Attendance entity=new Attendance();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public AttendanceDto create(AttendanceDto dto){
        return entityToDto(attendanceRepository.save(dtoToEntity(dto)));
    }

    public List<AttendanceDto> getAll(){
        List<AttendanceDto> dtos=new ArrayList<>();
        for(Attendance entity:attendanceRepository.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public AttendanceDto getByDateAndSub(String d, String subId) throws ParseException, UserNotFoundException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Subjects sub=subjectService.dtoToEntity(subjectService.getBySubCode(subId));
        Date date = formatter.parse(d);
        Attendance attendance=attendanceRepository.findByDateAndSubject(date,sub);
        if(attendance!=null)
             return entityToDto(attendance);
        else throw new UserNotFoundException("Not Found");

    }
    public Long attendancePerc(String reg){
        return attendanceRepository.countAbsencesByStudentRegNo(reg);
    }


    public void delete(Long id) throws UserNotFoundException {
        Attendance entity=attendanceRepository.findById(id).orElse(null);
        if(entity!=null)
            attendanceRepository.deleteById(id);
        else
            throw new UserNotFoundException("Attendance Id not Found");
    }


}
