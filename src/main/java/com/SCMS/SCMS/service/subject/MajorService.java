package com.SCMS.SCMS.service.subject;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.model.subject.MajorDto;
import com.SCMS.SCMS.repository.student.MajorRepository;

@Service
public class MajorService {
    @Autowired
    private MajorRepository majorRepository;

    public List<MajorDto> getAllMajor() {
        List<Major> majors = majorRepository.findAll();

        return majors.stream()
                .map(major -> new MajorDto(major.getMajorID(), major.getMajorName()))
                .collect(Collectors.toList());
    }
}
