package com.project.UDMS.repository;

import com.project.UDMS.entity.Attendance;
import com.project.UDMS.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query("SELECT COUNT(a) FROM Attendance a WHERE :studentRegNO MEMBER OF a.studentsAbsent")
    Long countAbsencesByStudentRegNo(@Param("studentRegNO") String regNo);

    Attendance findByDateAndSubject(Date date, Subjects sub);
}
