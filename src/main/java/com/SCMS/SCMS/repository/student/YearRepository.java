package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Year;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {

    Optional<Year> findByYearName(String yearName);
}