package com.project.UDMS.controler;

import com.project.UDMS.dto.SubjectsDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<SubjectsDto> create(@RequestBody @Valid SubjectsDto dto){
        return new ResponseEntity<>(subjectService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<SubjectsDto>> getAll(){
        return new ResponseEntity<>(subjectService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{subcode}")
    public ResponseEntity<SubjectsDto> getBySubCode(@PathVariable String subcode) throws UserNotFoundException {
        return new ResponseEntity<>(subjectService.getBySubCode(subcode),HttpStatus.OK);
    }
    @PutMapping("/update/{subcode}")
    public ResponseEntity<SubjectsDto> update(@RequestBody @Valid SubjectsDto dto,@PathVariable String subcode) throws UserNotFoundException {
        subjectService.getBySubCode(subcode);
        dto.setSubCode(subcode);
        return new ResponseEntity<>(subjectService.create(dto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{subcode}")
    public ResponseEntity<String> delete(@PathVariable String subcode) throws UserNotFoundException {
        subjectService.deleteBySubCode(subcode);
        return ResponseEntity.ok("deleted the record with id ="+subcode);
    }
}
