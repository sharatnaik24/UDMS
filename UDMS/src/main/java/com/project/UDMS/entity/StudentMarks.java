package com.project.UDMS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "marks")
public class StudentMarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mId;
    @OneToOne
    private StudentEnrollment regno;
    private int marks1;
    private int marks2;
    private int marks3;
    private int marks4;
}
