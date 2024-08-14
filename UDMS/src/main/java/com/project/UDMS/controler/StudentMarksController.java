package com.project.UDMS.controler;

import com.project.UDMS.dto.StudentMarksDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.StudentMarksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/marks")
public class StudentMarksController {
    @Autowired
    private StudentMarksService studentMarksService;

    @PostMapping("/create")
    public ResponseEntity<StudentMarksDto> create(@RequestBody @Valid StudentMarksDto dto){
        return new ResponseEntity<>(studentMarksService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<StudentMarksDto>> getAll(){
        return new ResponseEntity<>(studentMarksService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{reg}")
    public ResponseEntity<StudentMarksDto> getByRegNo(@PathVariable String reg) throws UserNotFoundException {
        return new ResponseEntity<>(studentMarksService.getByRegNo(reg),HttpStatus.OK);
    }
    @PutMapping("/update/{reg}")
    public ResponseEntity<StudentMarksDto> update(@RequestBody @Valid StudentMarksDto dto,@PathVariable String reg) throws UserNotFoundException {
        StudentMarksDto dto1= studentMarksService.getByRegNo(reg);
        dto.setMId(dto1.getMId());
        dto.setRegno(dto1.getRegno());
        return new ResponseEntity<>(studentMarksService.create(dto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{regno}")
    public ResponseEntity<String> delete(@PathVariable String reg) throws UserNotFoundException {
        studentMarksService.deleteByReg(reg);
        return ResponseEntity.ok("deleted the record with id ="+reg);
    }
}
