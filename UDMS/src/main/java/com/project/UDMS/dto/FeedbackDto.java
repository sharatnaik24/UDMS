package com.project.UDMS.dto;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.ProfessorDetails;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackDto {
    private int fID;
    private ProfessorDetails proffesor;
    @NotNull(message = "atleast you must enter your coursename")
    private CourseDetails coursename;
    @NotNull(message = "writeyour complaint/feedback")
    private String feedback;
}
