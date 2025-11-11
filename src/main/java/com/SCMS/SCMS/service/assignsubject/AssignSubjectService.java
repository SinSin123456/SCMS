package com.SCMS.SCMS.service.assignsubject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.entities.AssignSubject;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.entities.Teacher;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.assignsubject.AssignSubjectDto;
import com.SCMS.SCMS.model.request.assignsubject.ReqSaveAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ReqUpdateAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResListAssignSub;
import com.SCMS.SCMS.repository.assignsubject.AssignSubjectRepository;

import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.student.MajorRepository;
import com.SCMS.SCMS.repository.student.TeacherRepository;
import com.SCMS.SCMS.repository.student.YearRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AssignSubjectService {
    @Autowired
    private AssignSubjectRepository assignSubjectRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private StudentMangementRepository studentMangementRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public AssignSubjectDto addAssignSubject(ReqSaveAssignSub data) {

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        AssignSubject assignSubject = new AssignSubject();

        if ("STUDENT".equals(data.getRole())) {
            StudentMangement student = studentMangementRepository.findById(data.getStudentId())
                    .orElseThrow(() -> new NoSuchElementException("Student not found with ID " + data.getStudentId()));
            assignSubject.setStudent(student);
        }

        if ("TEACHER".equals(data.getRole())) {
            Teacher teacher = teacherRepository.findById(data.getTeacherId())
                    .orElseThrow(() -> new NoSuchElementException("Teacher not found with ID " + data.getTeacherId()));
            assignSubject.setTeacher(teacher);
        }

        Major major = majorRepository.findById(data.getMajorId())
                .orElseThrow(() -> new NoSuchElementException("Subject not found with ID " + data.getMajorId()));
        assignSubject.setMajor(major);

        assignSubject.setTerm(data.getTerm());

        assignSubject.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        assignSubject.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        assignSubject.setStatus(true);

        AssignSubject saved = assignSubjectRepository.save(assignSubject);

        return new AssignSubjectDto(saved);
    }

    public ResDatatableParam<ResListAssignSub> getAssignSubjectsList(ReqDatatableParam data) {
        List<AssignSubject> allAssign = assignSubjectRepository.findByStatusTrue();

        List<ResListAssignSub> dtos = allAssign.stream()
                .map(a -> new ResListAssignSub(
                        a.getId(),
                        a.getStudent() != null ? a.getStudent().getFullName() : "",
                        a.getTeacher() != null ? a.getTeacher().getFullName() : "",
                        a.getMajor() != null ? a.getMajor().getMajorName() : "", 
                        a.getTerm() != null ? a.getTerm() : ""))
                .collect(Collectors.toList());

        ResDatatableParam<ResListAssignSub> res = new ResDatatableParam<>();
        res.setDraw(data.getDraw());
        res.setRecordsTotal(dtos.size());
        res.setRecordsFiltered(dtos.size());
        res.setData(dtos);

        return res;
    }


    public ResponseEntity<ResEditAssignSub> editAssignSub(Long id) {
        AssignSubject assignSubject = assignSubjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assign Subject not found"));

        ResEditAssignSub res = new ResEditAssignSub();
        res.setTerm(assignSubject.getTerm());

        if (assignSubject.getStudent() != null) {
            res.setPersonId(assignSubject.getStudent().getStudentID());
            res.setPersonName(assignSubject.getStudent().getFullName());
            res.setRole("STUDENT");
        } else if (assignSubject.getTeacher() != null) {
            res.setPersonId(assignSubject.getTeacher().getTeacherID());
            res.setPersonName(assignSubject.getTeacher().getFullName());
            res.setRole("TEACHER");
        }

        if (assignSubject.getMajor() != null) {
            res.setMajorId(assignSubject.getMajor().getMajorID());
            res.setMajorName(assignSubject.getMajor().getMajorName());
        }

        return ResponseEntity.ok(res);
    }

    public AssignSubjectDto updateAssignSubject(ReqUpdateAssignSub data) {

        AssignSubject assignSubject = assignSubjectRepository.findById(data.getId())
                .orElseThrow(() -> new NoSuchElementException("Assign Subject not found with ID " + data.getId()));

        if ("STUDENT".equals(data.getRole())) {
            StudentMangement student = studentMangementRepository.findById(data.getStudentId())
                    .orElseThrow(() -> new NoSuchElementException("Student not found with ID " + data.getStudentId()));
            assignSubject.setStudent(student);
            assignSubject.setTeacher(null);
        } else if ("TEACHER".equals(data.getRole())) {
            Teacher teacher = teacherRepository.findById(data.getTeacherId())
                    .orElseThrow(() -> new NoSuchElementException("Teacher not found with ID " + data.getTeacherId()));
            assignSubject.setTeacher(teacher);
            assignSubject.setStudent(null);
        }

        Major major = majorRepository.findById(data.getMajorId())
                .orElseThrow(() -> new NoSuchElementException("Subject not found with ID " + data.getMajorId()));
        assignSubject.setMajor(major);

        assignSubject.setTerm(data.getTerm());
        assignSubject.setStatus(true);

        assignSubject.setUpdatedAt(
                data.getUpdatedAt() != null ? data.getUpdatedAt() : new Timestamp(System.currentTimeMillis()));
        assignSubject.setUpdatedBy(data.getUpdatedBy() != null ? data.getUpdatedBy()
                : SecurityContextHolder.getContext().getAuthentication().getName());

        AssignSubject saved = assignSubjectRepository.save(assignSubject);
        return new AssignSubjectDto(saved);
    }

    public AssignSubjectDto deleteAssign(Long id) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        AssignSubject assignSubject = assignSubjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("subject with id" + "not found"));

        assignSubject.setDeletedAt(now);
        assignSubject.setDeletedBy(authUsername);
        assignSubject.setStatus(false);

        return new AssignSubjectDto(assignSubjectRepository.save(assignSubject));
    }
}
