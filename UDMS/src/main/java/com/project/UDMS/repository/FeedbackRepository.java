package com.project.UDMS.repository;

import com.project.UDMS.entity.CourseDetails;
import com.project.UDMS.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
}
