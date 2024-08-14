package com.project.UDMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "professor")
public class ProfessorDetails {
    @Id
    private String pId;
    private String name;
    private String qualification;
    private String mail;
    private String mob;
}
