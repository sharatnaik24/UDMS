package com.project.UDMS.repository;

import com.project.UDMS.entity.Feedback;
import com.project.UDMS.entity.ProfessorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorDetails,String> {
}
