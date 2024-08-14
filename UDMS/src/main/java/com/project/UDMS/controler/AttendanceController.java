package com.project.UDMS.controler;

import com.project.UDMS.dto.AttendanceDto;
import com.project.UDMS.entity.Subjects;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AttendanceDto>> getAll() {
        List<AttendanceDto> attendanceDtos = attendanceService.getAll();
        return ResponseEntity.ok(attendanceDtos);
    }

    @PostMapping("/create")
    public ResponseEntity<AttendanceDto> create(@RequestBody AttendanceDto attendanceDto) {
        AttendanceDto createdAttendance = attendanceService.create(attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);
    }

    @GetMapping("/getAttendanceByDateAndSubject/{date},{subject}")
    public ResponseEntity<AttendanceDto> getAttendanceByDateAndSubject(@PathVariable String date, @PathVariable String subject) throws ParseException, UserNotFoundException {
        AttendanceDto attendanceDto = attendanceService.getByDateAndSub(date, subject);
        return new ResponseEntity<>(attendanceDto,HttpStatus.OK);
    }
    @GetMapping("getRegPer/{reg}")
    public ResponseEntity<Long> getRegPer(@PathVariable String reg){
        return new ResponseEntity<>(attendanceService.attendancePerc(reg),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Long id) throws UserNotFoundException {
        attendanceService.delete(id);
        return ResponseEntity.ok("deleted successfully");
    }

    @PutMapping("/update/{date},{subject}")
    public ResponseEntity<AttendanceDto> update(@RequestBody AttendanceDto dto,@PathVariable String date, @PathVariable String subject) throws UserNotFoundException, ParseException {
        AttendanceDto dto1=attendanceService.getByDateAndSub(date,subject);
        dto.setAbsenteeId(dto1.getAbsenteeId());
        return new ResponseEntity<>(attendanceService.create(dto),HttpStatus.OK);

    }
}
