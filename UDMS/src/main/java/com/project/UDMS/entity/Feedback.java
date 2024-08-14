package com.project.UDMS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fID;
    @ManyToOne
    private ProfessorDetails proffesor;
    @ManyToOne
    private CourseDetails coursename;
    private String feedback;
}
