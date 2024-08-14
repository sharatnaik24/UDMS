package com.project.UDMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="courses")
public class CourseDetails {
    @Id
    private String courseName;
    @OneToMany
    private List<Subjects> subjects;

}
