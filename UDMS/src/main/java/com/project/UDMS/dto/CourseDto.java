package com.project.UDMS.dto;

import com.project.UDMS.entity.Subjects;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CourseDto {
    private String courseName;
    @NotNull(message = "enter 4 subjects")
    private List<Subjects> subjects;

}
