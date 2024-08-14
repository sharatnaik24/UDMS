package com.project.UDMS.controler;

import com.project.UDMS.dto.FeedbackDto;
import com.project.UDMS.exception.UserNotFoundException;
import com.project.UDMS.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<FeedbackDto> create(@RequestBody @Valid FeedbackDto dto){
        return new ResponseEntity<>(feedbackService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<FeedbackDto>> getAll(){
        return new ResponseEntity<>(feedbackService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{fId}")
    public ResponseEntity<FeedbackDto> getByRegNo(@PathVariable int fId) throws UserNotFoundException {
        return new ResponseEntity<>(feedbackService.getByRegNo(fId),HttpStatus.OK);
    }
    @PutMapping("/update/{fId}")
    public ResponseEntity<FeedbackDto> update(@RequestBody @Valid FeedbackDto dto,@PathVariable int fId) throws UserNotFoundException {
        feedbackService.getByRegNo(fId);
        dto.setFID(fId);
        return new ResponseEntity<>(feedbackService.create(dto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{fId}")
    public ResponseEntity<String> delete(@PathVariable int fId) throws UserNotFoundException {
        feedbackService.deleteByRegNo(fId);
        return ResponseEntity.ok("deleted the record with id ="+fId);
    }
}
