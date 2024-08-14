package com.project.UDMS.repository;

import com.project.UDMS.entity.Feedback;
import com.project.UDMS.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects,String> {
}
