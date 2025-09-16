package com.SCMS.SCMS.service.student;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.SCMS.SCMS.entities.SchoolClass;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Subject;
import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.request.student.ReqSaveStudent;
import com.SCMS.SCMS.model.request.student.ReqUpdateStudent;
import com.SCMS.SCMS.model.request.student.ResEditStudent;
import com.SCMS.SCMS.model.request.student.ResListStudent;
import com.SCMS.SCMS.model.response.admin.ResListUsers;
import com.SCMS.SCMS.repository.student.SchoolClassRepository;
import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.student.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentMangementService {

    @Autowired
    private StudentMangementRepository studentMangementRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentMangementService.class);

    public ResponseEntity<Map<String, Object>> addStudent(ReqSaveStudent data) {
        Map<String, Object> response = new HashMap<>();
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (data.getFullName() == null || data.getFullName().isBlank()) {
            response.put("success", false);
            response.put("message", "Full name is required");
            return ResponseEntity.badRequest().body(response);
        }

       
        if (data.getClassName() == null || data.getClassName().isBlank()) {
            response.put("success", false);
            response.put("message", "Class name is required");
            return ResponseEntity.badRequest().body(response);
        }

      
        Optional<SchoolClass> optionalClass = schoolClassRepository.findByClassName(data.getClassName());
        if (optionalClass.isEmpty()) {
            response.put("success", false);
            response.put("message", "Class not found");
            return ResponseEntity.badRequest().body(response);
        }

        StudentMangement student = new StudentMangement();
        student.setFullName(data.getFullName());
        student.setEmail(data.getEmail());
        student.setPhone(data.getPhone());
        student.setSex(data.getSex());
        student.setRegisterDate(data.getRegisterDate());
        student.setCreatedAt(now);
        student.setCreatedBy(authUsername);

        if (student.getStatus() == null) {
            student.setStatus(true);
        }
        student.setSchoolClass(optionalClass.get());

       
        Set<Subject> subjects = new HashSet<>();
        if (data.getSubjectNames() != null && !data.getSubjectNames().isEmpty()) {
            for (String name : data.getSubjectNames()) {
                if (name != null && !name.isBlank()) {
                    subjectRepository.findBySubjectName(name.trim())
                            .ifPresent(subjects::add);
                }
            }
        }
        student.setSubjects(subjects);

        studentMangementRepository.save(student);

        response.put("success", true);
        response.put("message", "Student added successfully");
        response.put("studentId", student.getStudentID());

        return ResponseEntity.ok(response);
    }

    public ResDatatableParam<ResListStudent> getStudentsList(ReqDatatableParam data) {
        try {
            String searchValue = data.getSearch() != null ? data.getSearch().getValue() : null;
            int start = data.getStart();
            int length = data.getLength();
            if (length < 1) {
                length = 10;
            }
            int page = start / length;

            logger.debug("DataTable Request: draw={}, start={}, length={}, search={}",
                    data.getDraw(), start, length, searchValue);

            PageRequest pageRequest = PageRequest.of(page, length);

            List<StudentMangement> allStudents = studentMangementRepository.findAllStudents(searchValue, pageRequest);
            int countAllStudents = studentMangementRepository.countAllStudents(searchValue);

            logger.debug("Students Found: size={}, Total Count: {}", allStudents.size(), countAllStudents);

            List<ResListStudent> studentList = new ArrayList<>();
            for (StudentMangement student : allStudents) {
                logger.debug("Processing Student: id={}, name={}", student.getStudentID(), student.getFullName());

                // map subjects to names (if any)
                List<String> subjectNames = student.getSubjects()
                        .stream()
                        .map(Subject::getSubjectName)
                        .toList();

                studentList.add(new ResListStudent(
                        student.getStudentID(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getSex(),
                        student.getSchoolClass() != null ? student.getSchoolClass().getClassName() : null,
                        subjectNames,
                        student.getCreatedAt()));
            }

            logger.debug("Processed Student List Size: {}", studentList.size());

            return new ResDatatableParam<>(data.getDraw(), countAllStudents, countAllStudents, studentList);
        } catch (Exception e) {
            logger.error("Error in getStudentsList: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch students: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<ResEditStudent> editStudent(Long id) {
        
        StudentMangement student = studentMangementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        ResEditStudent getstudent = new ResEditStudent();
        getstudent.setId(student.getStudentID());
        getstudent.setFullName(student.getFullName());
        getstudent.setSex(student.getSex());
        getstudent.setEmail(student.getEmail());
        getstudent.setPhone(student.getPhone());
        getstudent.setRegisterDate(student.getRegisterDate());

        if (student.getSchoolClass() != null) {
            getstudent.setClassId(student.getSchoolClass().getClassID()); 
            getstudent.setClassName(student.getSchoolClass().getClassName());
        }

        if (student.getSubjects() != null && !student.getSubjects().isEmpty()) {
            List<Integer> subjectIds = student.getSubjects().stream()
                    .map((Subject sub) -> sub.getSubjectID().intValue()) 
                    .collect(Collectors.toList());
            getstudent.setSubjectIds(subjectIds);

            List<String> subjectNames = student.getSubjects().stream()
                    .map((Subject sub) -> sub.getSubjectName())
                    .collect(Collectors.toList());
            getstudent.setSubjectNames(subjectNames);
        }

        return ResponseEntity.ok(getstudent);
    }


    public ResponseEntity<Map<String, Object>> updateStudent(ReqUpdateStudent data) {
        Map<String, Object> response = new HashMap<>();
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

     
        if (data.getFullName() == null || data.getFullName().isBlank()) {
            response.put("success", false);
            response.put("message", "Full name is required");
            return ResponseEntity.badRequest().body(response);
        }

        if (data.getClassName() == null || data.getClassName().isBlank()) {
            response.put("success", false);
            response.put("message", "Class name is required");
            return ResponseEntity.badRequest().body(response);
        }

 
        Optional<SchoolClass> optionalClass = schoolClassRepository.findByClassName(data.getClassName());
        if (optionalClass.isEmpty()) {
            response.put("success", false);
            response.put("message", "Class not found with name " + data.getClassName());
            return ResponseEntity.badRequest().body(response);
        }

      
        StudentMangement student = studentMangementRepository.findById(data.getId())
                .orElseThrow(() -> new NoSuchElementException("Student with ID " + data.getId() + " not found"));

 
        student.setFullName(data.getFullName());
        student.setSex(data.getSex());
        student.setEmail(data.getEmail());
        student.setPhone(data.getPhone());
        student.setRegisterDate(data.getRegisterDate());
        student.setUpdatedAt(now);
        student.setUpdatedBy(authUsername);
        student.setSchoolClass(optionalClass.get());
        student.setStatus(true);

       
        Set<Subject> subjects = new HashSet<>();
        if (data.getSubjectNames() != null && !data.getSubjectNames().isEmpty()) {
            for (String name : data.getSubjectNames()) {
                subjectRepository.findBySubjectName(name.trim()).ifPresent(subjects::add);
            }
        }
        student.setSubjects(subjects);
      

      
        studentMangementRepository.save(student);

        response.put("success", true);
        response.put("message", "Student updated successfully");
        response.put("studentId", student.getStudentID());

        return ResponseEntity.ok(response);
    }

    public StudentMangement deleteStudent (Long id) {

        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        StudentMangement student = studentMangementRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Student with id" + "not found"));

        student.setStatus(false);
        student.setDeletedAt(now);
        student.setDeletedBy(authUsername);

        return studentMangementRepository.save(student);
    }
}
