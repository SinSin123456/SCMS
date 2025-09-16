package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SCMS.SCMS.entities.Teacher;

public interface TeacherRepository  extends JpaRepository <Teacher, Long>{
    Optional<Teacher> findByFullName(String fullName);

    Optional<Teacher> findByUserId(Long userId);
}
