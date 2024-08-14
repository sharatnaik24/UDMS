package com.project.UDMS.repository;

import com.project.UDMS.entity.Feedback;
import com.project.UDMS.entity.StudentEnrollment;
import com.project.UDMS.entity.StudentMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentMarksRepository extends JpaRepository<StudentMarks,Integer> {
    Optional<StudentMarks> findByRegno(StudentEnrollment student);
}
