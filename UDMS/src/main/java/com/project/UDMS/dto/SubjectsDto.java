package com.project.UDMS.dto;

import com.project.UDMS.entity.ProfessorDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectsDto {
    private String subCode;

    @NotBlank(message = "subName is Compulsory")
    private String subName;

    @NotNull(message = "mention its type")
    private Character subType;

    @NotNull(message = "assign the professor")
    private ProfessorDetails professorId;
}
