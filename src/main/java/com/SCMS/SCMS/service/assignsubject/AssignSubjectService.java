package com.SCMS.SCMS.service.assignsubject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.entities.AssignSubject;
import com.SCMS.SCMS.entities.Day;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Subjects;
import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.entities.Teacher;
import com.SCMS.SCMS.entities.TimeSlot;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.assignsubject.AssignSubjectDto;
import com.SCMS.SCMS.model.request.assignsubject.ReqSaveAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ReqUpdateAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResListAssignSub;
import com.SCMS.SCMS.model.subject.DayDto;
import com.SCMS.SCMS.model.subject.SubjectDto;
import com.SCMS.SCMS.model.subject.TeacherDto;
import com.SCMS.SCMS.model.subject.TimeSlotDto;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import com.SCMS.SCMS.repository.assignsubject.AssignSubjectRepository;

import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.student.SubjectRepository;
import com.SCMS.SCMS.repository.student.DayRepository;
import com.SCMS.SCMS.repository.student.MajorRepository;
import com.SCMS.SCMS.repository.student.TeacherRepository;
import com.SCMS.SCMS.repository.student.TimeSlotRepository;
import com.SCMS.SCMS.repository.student.YearRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AssignSubjectService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public List<TeacherDto> getTeacherDropdown() {
        return usersRepository.findAll().stream()
                .filter(u -> u.getRoles() != null
                        && u.getRoles().contains("TEACHER")
                        && u.getStatus() == true)
                .map(u -> new TeacherDto(
                        u.getId(),
                        u.getFullname()))
                .collect(Collectors.toList());
    }

    public List<SubjectDto> getAllSubjects() {
        List<Subjects> subjects = subjectRepository.findAll();

        return subjects.stream()
                .filter(s -> Boolean.TRUE.equals(s.getStatus()))
                .map(subject -> new SubjectDto(subject.getId(), subject.getSubjectName()))
                .collect(Collectors.toList());
    }

    public List<DayDto> getAllDay() {
        List<Day> days = dayRepository.findAll();

        return days.stream()
                .map(day -> new DayDto(day.getId(), day.getDayName()))
                .collect(Collectors.toList());
    }

    public List<TimeSlotDto> geetAllTimeSlot() {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();

        return timeSlots.stream()
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getSlotName()))
                .collect(Collectors.toList());
    }
}
