package com.project.UDMS.controler;

import com.project.UDMS.dto.StudentEnrollmentDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.StudentEnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/students")
public class StudentEnrollController {
    @Autowired
    private StudentEnrollmentService studentEnrollmentService;

    @PostMapping("/create")
    public ResponseEntity<StudentEnrollmentDto> create(@RequestBody @Valid StudentEnrollmentDto dto){
        return new ResponseEntity<>(studentEnrollmentService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<StudentEnrollmentDto>> getAll(){
        return new ResponseEntity<>(studentEnrollmentService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{reg}")
    public ResponseEntity<StudentEnrollmentDto> getByRegNo(@PathVariable String reg) throws UserNotFoundException {
        return new ResponseEntity<>(studentEnrollmentService.getByRegNo(reg),HttpStatus.OK);
    }
    @PutMapping("/update/{reg}")
    public ResponseEntity<StudentEnrollmentDto> update(@RequestBody @Valid StudentEnrollmentDto dto,@PathVariable String reg) throws UserNotFoundException {
        studentEnrollmentService.getByRegNo(reg);
        dto.setRollNo(reg);
        return new ResponseEntity<>(studentEnrollmentService.create(dto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{regno}")
    public ResponseEntity<String> delete(@PathVariable String regno) throws UserNotFoundException {
        studentEnrollmentService.deleteByRegNo(regno);
        return ResponseEntity.ok("deleted the record with id ="+regno);
    }
}
