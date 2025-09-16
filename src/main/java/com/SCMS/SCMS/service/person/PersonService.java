package com.SCMS.SCMS.service.person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.model.subject.PersonDto;
import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.student.TeacherRepository;

@Service
public class PersonService {
    @Autowired
    private StudentMangementRepository studentMangementRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public List<PersonDto> getAllPersons() {
        List<PersonDto> persons = new ArrayList<>();

       
        studentMangementRepository.findAll().forEach(student -> {
            persons.add(new PersonDto(student.getStudentID().intValue(), student.getFullName(), "STUDENT"));
        });

        teacherRepository.findAll().forEach(teacher -> {
            persons.add(new PersonDto(teacher.getTeacherID().intValue(), teacher.getFullName(), "TEACHER"));
        });

        return persons;
    }
}
