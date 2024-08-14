package com.project.UDMS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class StudentEnrollment {
    @Id
    private String rollNo;
    private String name;
    private String mail;
    private String mob;
    @ManyToOne
    private CourseDetails course;

}
