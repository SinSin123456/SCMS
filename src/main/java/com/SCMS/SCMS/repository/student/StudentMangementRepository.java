package com.SCMS.SCMS.repository.student;

import java.util.List;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.StudentMangement;

@Repository
public interface StudentMangementRepository extends JpaRepository<StudentMangement, Long> {

        // boolean existsByEmail(String email);

        @Query("SELECT s FROM StudentMangement s " +
                        "LEFT JOIN s.year c " +
                        "WHERE s.status = true AND (" +
                        ":search IS NULL OR " +
                        "s.fullName LIKE %:search% OR " +
                        // "s.email LIKE %:search% OR " +
                        "c.yearName LIKE %:search%)")
        List<StudentMangement> findAllStudents(@Param("search") String search, Pageable pageable);

        @Query("SELECT COUNT(s) FROM StudentMangement s " +
                        "LEFT JOIN s.year c " +
                        "WHERE s.status = true AND (" +
                        ":search IS NULL OR " +
                        "s.fullName LIKE %:search% OR " +
                        // "s.email LIKE %:search% OR " +
                        "c.yearName LIKE %:search%)")

        int countAllStudents(@Param("search") String search);

        @Query("SELECT s FROM StudentMangement s WHERE s.studentID = :id")
        StudentMangement findStudentById(@Param("id") Long id);

        @Query("SELECT s FROM StudentMangement s")
        List<StudentMangement> findAllStudents();
}
