package com.project.UDMS.controler;

import com.project.UDMS.dto.ProfessorDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/faculty")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @PostMapping("/create")
    public ResponseEntity<ProfessorDto> create(@RequestBody @Valid ProfessorDto dto){
        return new ResponseEntity<>(professorService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProfessorDto>> getAll(){
        return new ResponseEntity<>(professorService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{pId}")
    public ResponseEntity<ProfessorDto> getByRegNo(@PathVariable String pId) throws UserNotFoundException {
        return new ResponseEntity<>(professorService.getByRegNo(pId),HttpStatus.OK);
    }
    @PutMapping("/update/{pId}")
    public ResponseEntity<ProfessorDto> update(@RequestBody @Valid ProfessorDto dto,@PathVariable String pId) throws UserNotFoundException {
        professorService.getByRegNo(pId);
        dto.setPId(pId);
        return new ResponseEntity<>(professorService.create(dto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{pId}")
    public ResponseEntity<String> delete(@PathVariable String pId) throws UserNotFoundException {
        professorService.deleteByRegNo(pId);
        return ResponseEntity.ok("deleted the record with id ="+pId);
    }
}
