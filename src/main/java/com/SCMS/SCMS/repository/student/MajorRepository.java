package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {


    Optional<Major> findByMajorName(String majorName);

}

