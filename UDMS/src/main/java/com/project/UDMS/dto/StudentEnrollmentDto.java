package com.project.UDMS.dto;

import com.project.UDMS.entity.CourseDetails;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StudentEnrollmentDto {
    private String rollNo;
    @NotBlank(message = "name is compulsary")
    private String name;
    @Email
    private String mail;
    @Pattern(regexp = "^\\d{10}$",message = "invalid mob number")
    private String mob;
    @NotNull(message = "this field cant be blank")
    private CourseDetails course;

}
