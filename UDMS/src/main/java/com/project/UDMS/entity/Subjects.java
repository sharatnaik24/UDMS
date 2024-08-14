package com.project.UDMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "subjects")
public class Subjects {
    @Id
    private String subCode;
    private String subName;
    private Character subType;
    @ManyToOne
    private ProfessorDetails professorId;
}
