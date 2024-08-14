package com.project.UDMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProfessorDto {
    private String pId;
    @NotBlank(message = "this fields cant be blank")
    private String name;
    @NotBlank(message = "this fields cant be blank")
    private String qualification;
    @Email
    private String mail;
    @Pattern(regexp = "^\\d{10}$",message = "invalid mob number")
    private String mob;
}
