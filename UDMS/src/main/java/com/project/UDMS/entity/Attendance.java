package com.project.UDMS.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Entity
@Data
public class Attendance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long absenteeId;

    @Temporal(TemporalType.DATE)
    private Date date=new Date(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "course_name", nullable = false)
    private CourseDetails course;

    @ManyToOne
    @JoinColumn(name = "subject", nullable = false)
    private Subjects subject;

    private int present;
    private int absent;

    @ElementCollection
    private List<String> studentsAbsent;

}
