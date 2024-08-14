package com.project.UDMS.controler;

import com.project.UDMS.dto.CourseDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<CourseDto> create(@RequestBody @Valid CourseDto dto){
        return new ResponseEntity<>(courseService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<CourseDto>> getAll(){
        return new ResponseEntity<>(courseService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{course}")
    public ResponseEntity<CourseDto> getByRegNo(@PathVariable String course) throws UserNotFoundException {
        return new ResponseEntity<>(courseService.getByRegNo(course),HttpStatus.OK);
    }
    @PutMapping("/update/{course}")
    public ResponseEntity<CourseDto> update(@RequestBody @Valid CourseDto dto,@PathVariable String course) throws UserNotFoundException {
        courseService.getByRegNo(course);
        dto.setCourseName(course);
        return new ResponseEntity<>(courseService.create(dto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{course}")
    public ResponseEntity<String> delete(@PathVariable String course) throws UserNotFoundException {
        courseService.deleteByRegNo(course);
        return ResponseEntity.ok("deleted the record with name ="+course);
    }
}
