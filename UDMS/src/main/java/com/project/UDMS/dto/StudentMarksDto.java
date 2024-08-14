package com.project.UDMS.dto;

import com.project.UDMS.entity.StudentEnrollment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentMarksDto {
    private int mId;

    @NotNull(message = "enter the regno properly")
    private StudentEnrollment regno;

    @Max(value = 100, message = "marks must be less than or equal to 100")
    private int marks1;

    @Max(value = 100, message = "marks must be less than or equal to 100")
    private int marks2;

    @Max(value = 100, message = "marks must be less than or equal to 100")
    private int marks3;

    @Max(value = 100, message = "marks must be less than or equal to 100")
    private int marks4;
}
