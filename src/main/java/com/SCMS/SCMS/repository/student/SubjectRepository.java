package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {


    Optional<Subject> findBySubjectName(String subjectName);

}

