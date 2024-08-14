package com.project.UDMS.dto;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.Subjects;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AttendanceDto {
    private Long absenteeId;

    @NotNull(message = "Date cannot be null")
    private Date date;

    @NotNull(message = "Course name cannot be null")
    private CourseDetails course;

    @NotNull(message = "Subject ID cannot be null")
    private Subjects subject;
    private int present;
    private int absent;

    @NotNull(message = "List of absent students cannot be null")
    private List<String> studentsAbsent;
}
