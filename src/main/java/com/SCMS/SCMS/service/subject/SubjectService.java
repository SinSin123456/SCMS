package com.SCMS.SCMS.service.subject;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.entities.Subject;
import com.SCMS.SCMS.model.subject.SubjectDto;
import com.SCMS.SCMS.repository.student.SubjectRepository;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectDto> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(subject -> new SubjectDto(subject.getSubjectID(), subject.getSubjectName()))
                .collect(Collectors.toList());
    }
}
