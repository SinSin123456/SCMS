package com.SCMS.SCMS.repository.student;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.StudentMangement;

@Repository
public interface StudentMangementRepository extends JpaRepository<StudentMangement, Long> {

        // fetch paginated students with their major names concatenated
        @Query("""
                        SELECT s.studentID AS studentID,
                               s.fullName AS fullName,
                               s.phone AS phone,
                               s.sex AS sex,
                               y.yearName AS yearName,
                               GROUP_CONCAT(m.majorName) AS majorNames,
                               s.createdAt AS createdAt
                        FROM StudentMangement s
                        LEFT JOIN s.majors m
                        LEFT JOIN s.year y
                        WHERE (:search IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
                              OR LOWER(y.yearName) LIKE LOWER(CONCAT('%', :search, '%')))
                        GROUP BY s.studentID, s.fullName, s.phone, s.sex, y.yearName, s.createdAt
                        """)
        List<Map<String, Object>> findStudentsWithMajorsPaginated(@Param("search") String search, Pageable pageable);

        @Query("""
                        SELECT COUNT(s.studentID)
                        FROM StudentMangement s
                        LEFT JOIN s.year y
                        WHERE (:search IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
                              OR LOWER(y.yearName) LIKE LOWER(CONCAT('%', :search, '%')))
                        """)
        int countAllStudents(@Param("search") String search);

        @Query("SELECT s FROM StudentMangement s WHERE s.studentID = :id")
        StudentMangement findStudentById(@Param("id") Long id);

        @Query("SELECT s FROM StudentMangement s")
        List<StudentMangement> findAllStudents();
}