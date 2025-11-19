package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Subjects;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects, Long>{
    Optional<Subjects> findBySubjectName(String subjectName);
} 
