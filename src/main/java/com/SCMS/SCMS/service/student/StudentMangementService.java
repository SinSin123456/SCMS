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
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Objects;

import org.hsqldb.rights.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.SCMS.SCMS.entities.Year;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.request.student.ReqSaveStudent;
import com.SCMS.SCMS.model.request.student.ReqUpdateStudent;
import com.SCMS.SCMS.model.request.student.ResEditStudent;
import com.SCMS.SCMS.model.request.student.ResListStudent;
import com.SCMS.SCMS.model.request.student.StudentDto;
import com.SCMS.SCMS.model.response.admin.ResListUsers;
import com.SCMS.SCMS.model.subject.MajorDto;
import com.SCMS.SCMS.repository.student.YearRepository;
import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import com.SCMS.SCMS.repository.student.MajorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentMangementService {

    @Autowired
    private StudentMangementRepository studentMangementRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentMangementService.class);

    public ResponseEntity<Map<String, Object>> addStudent(ReqSaveStudent data) {
        Map<String, Object> response = new HashMap<>();
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (data.getUserId() == null) {
            response.put("success", false);
            response.put("message", "Student is required");
            return ResponseEntity.badRequest().body(response);
        }

        Users user = usersRepository.findById(data.getUserId())
                .orElse(null);
        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }

        // Validate Year ID
        if (data.getYearId() == null) {
            response.put("success", false);
            response.put("message", "Year is required");
            return ResponseEntity.badRequest().body(response);
        }
        Optional<Year> optionalYear = yearRepository.findById(data.getYearId());
        if (optionalYear.isEmpty()) {
            response.put("success", false);
            response.put("message", "Class not found");
            return ResponseEntity.badRequest().body(response);
        }

        StudentMangement student = new StudentMangement();
        student.setFullName(user.getFullname());
        student.setPhone(data.getPhone());
        student.setSex(data.getSex());
        student.setRegisterDate(data.getRegisterDate());
        student.setCreatedAt(now);
        student.setCreatedBy(authUsername);
        student.setStatus(true);
        student.setYear(optionalYear.get());

        Set<Major> majors = new HashSet<>();
        if (data.getMajorName() != null) {
            for (String idStr : data.getMajorName()) {
                try {
                    Long majorId = Long.parseLong(idStr.trim());
                    majorRepository.findById(majorId).ifPresent(majors::add);
                } catch (NumberFormatException e) {
                }
            }
        }
        student.setMajor(majors);

        studentMangementRepository.save(student);

        List<String> majorNames = majors.stream()
                .map(Major::getMajorName)
                .collect(Collectors.toList());

        response.put("success", true);
        response.put("message", "Student added successfully");
        response.put("studentId", student.getStudentID());
        response.put("majors", majorNames);

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
                List<String> majorName = student.getMajor()
                        .stream()
                        .map(Major::getMajorName)
                        .toList();

                studentList.add(new ResListStudent(
                        student.getStudentID(),
                        student.getFullName(),

                        student.getPhone(),
                        student.getSex(),
                        student.getYear() != null ? student.getYear().getYearName() : null,
                        majorName,
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

        getstudent.setPhone(student.getPhone());
        getstudent.setRegisterDate(student.getRegisterDate());

        if (student.getYear() != null) {
            getstudent.setId(student.getYear().getYearID());
            getstudent.setYearName(student.getYear().getYearName());
        }

        if (student.getMajor() != null && !student.getMajor().isEmpty()) {
            List<Integer> majorIds = student.getMajor().stream()
                    .map((Major sub) -> sub.getMajorID().intValue())
                    .collect(Collectors.toList());
            getstudent.setMajorIds(majorIds);

            List<String> majorName = student.getMajor().stream()
                    .map((Major sub) -> sub.getMajorName())
                    .collect(Collectors.toList());
            getstudent.setMajorName(majorName);
        }

        return ResponseEntity.ok(getstudent);
    }

    public ResponseEntity<Map<String, Object>> updateStudent(ReqUpdateStudent data) {

        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate student ID
            if (data.getStudentId() == null) {
                response.put("success", false);
                response.put("code", "400");
                response.put("message", "Student ID is required");
                return ResponseEntity.badRequest().body(response);
            }

            // Find existing student
            StudentMangement student = studentMangementRepository.findById(data.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            // Update basic fields
            student.setFullName(data.getFullName());
            student.setPhone(data.getPhone());
            student.setSex(data.getSex());
            student.setRegisterDate(data.getRegisterDate());
            student.setStatus(true);
            student.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            student.setUpdatedBy(authUsername);

            // Update relations
            if (data.getUserId() != null) {
                Users user = usersRepository.findById(data.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                student.setUser(user);
            }

            if (data.getYearId() != null) {
                yearRepository.findById(data.getYearId()).ifPresent(student::setYear);
            }

            // Update majors by ID
            if (data.getMajorId() != null && !data.getMajorId().isEmpty()) {
                Set<Major> majors = data.getMajorId().stream()
                        .map(id -> majorRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Major not found: " + id)))
                        .collect(Collectors.toSet());
                student.setMajor(majors);
            }

            // Optional: update majors by Name (if provided)
            if (data.getMajorName() != null && !data.getMajorName().isEmpty()) {
                Set<Major> majorsByName = data.getMajorName().stream()
                        .map(name -> majorRepository.findByMajorName(name)
                                .orElseThrow(() -> new RuntimeException("Major not found: " + name)))
                        .collect(Collectors.toSet());
                student.setMajor(majorsByName);
            }

            studentMangementRepository.save(student);

            response.put("success", true);
            response.put("code", "");
            response.put("message", "Student updated successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("code", "500");
            response.put("message", "Server error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public List<StudentDto> getStudentDropdown() {
        return usersRepository.findAll().stream()
                .filter(u -> u.getRoles() != null
                        && u.getRoles().contains("STUDENT")
                        && u.getStatus() == true)
                .map(u -> new StudentDto(
                        u.getId(),
                        u.getFullname()))
                .collect(Collectors.toList());
    }


    public ResponseEntity<Map<String, Object>> deletestudent(Long id) {
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        StudentMangement student = studentMangementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));

        student.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        student.setStatus(false);
        student.setDeletedBy(authUsername);

        studentMangementRepository.save(student);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Student deleted successfully");

        return ResponseEntity.ok(response);
    }

    // public TodoList deleteTask(Long id) {
    //     TodoList todoList = todoListRepository.findById(id)
    //             .orElseThrow(() -> new NoSuchElementException("Todo list with id" + "not found"));

    //     todoList.setDelete(true);

    //     return todoListRepository.save(todoList);
    // }
}
