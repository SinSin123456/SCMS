package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Day;

@Repository
public interface DayRepository extends JpaRepository<Day, Long>{

    Optional<Day> findByDayName(String dayName);    
} 
