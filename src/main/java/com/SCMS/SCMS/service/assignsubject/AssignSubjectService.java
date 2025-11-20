package com.SCMS.SCMS.service.assignsubject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.SCMS.SCMS.entities.Room;
import com.SCMS.SCMS.entities.Teacher;
import com.SCMS.SCMS.entities.TimeSlot;
import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.entities.Year;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.assignsubject.AssignSubjectDto;
import com.SCMS.SCMS.model.request.assignsubject.ReqSaveAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ReqUpdateAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResListAssignSub;
import com.SCMS.SCMS.model.subject.DayDto;
import com.SCMS.SCMS.model.subject.RoomDto;
import com.SCMS.SCMS.model.subject.SubjectDto;
import com.SCMS.SCMS.model.subject.TeacherDto;
import com.SCMS.SCMS.model.subject.TimeSlotDto;
import com.SCMS.SCMS.model.subject.YearDto;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import com.SCMS.SCMS.repository.assignsubject.AssignSubjectRepository;

import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.student.SubjectRepository;
import com.SCMS.SCMS.repository.student.DayRepository;
import com.SCMS.SCMS.repository.student.MajorRepository;
import com.SCMS.SCMS.repository.student.RoomRepository;
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

        @Autowired
        private RoomRepository roomRepository;

        @Autowired
        private YearRepository yearRepository;

        @Autowired
        private MajorRepository majorRepository;

        @Autowired
        private TeacherRepository teacherRepository;

        @Autowired
        private AssignSubjectRepository assignSubjectRepository;

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

        public List<RoomDto> getAllRoom() {
                List<Room> rooms = roomRepository.findAll();

                return rooms.stream()
                                .map(room -> new RoomDto(room.getId(), room.getRoomName()))
                                .collect(Collectors.toList());
        }

        public List<YearDto> getAllYear() {
                List<Year> years = yearRepository.findAll();
                return years.stream()
                                .map(year -> new YearDto(year.getYearID(), year.getYearName()))
                                .collect(Collectors.toList());
        }

        public ResponseEntity<Map<String, Object>> addAssignSubject(ReqSaveAssignSub data) {
                Map<String, Object> response = new HashMap<>();
                String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
                Timestamp now = Timestamp.valueOf(LocalDateTime.now());

                // Validate teacherId
                if (data.getTeacherId() == null) {
                        response.put("success", false);
                        response.put("message", "Teacher is required");
                        return ResponseEntity.badRequest().body(response);
                }

                Users teacher = usersRepository.findByIdAndRole(data.getTeacherId(), "TEACHER").orElse(null);
                if (teacher == null) {
                        response.put("success", false);
                        response.put("message", "Teacher not found");
                        return ResponseEntity.badRequest().body(response);
                }

                // validation year
                if (data.getYearId() == null) {
                        response.put("success", false);
                        response.put("message", "Year is required");
                        return ResponseEntity.badRequest().body(response);
                }
                Year year = yearRepository.findById(data.getYearId()).orElse(null);
                if (year == null) {
                        response.put("success", false);
                        response.put("message", "Year not found");
                        return ResponseEntity.badRequest().body(response);
                }

                if (data.getMajorId() == null) {
                        response.put("success", false);
                        response.put("message", "Major is required");
                        return ResponseEntity.badRequest().body(response);
                }

                Major major = majorRepository.findById(data.getMajorId()).orElse(null);
                if (major == null) {
                        response.put("success", false);
                        response.put("message", "Major not found");
                        return ResponseEntity.badRequest().body(response);
                }

                // validation subject
                if (data.getSubjectId() == null) {
                        response.put("success", false);
                        response.put("message", "Subject is required");
                        return ResponseEntity.badRequest().body(response);
                }

                Subjects subject = subjectRepository.findById(data.getSubjectId()).orElse(null);
                if (subject == null) {
                        response.put("success", false);
                        response.put("message", "Subject not found");
                        return ResponseEntity.badRequest().body(response);
                }

                // validation day
                if (data.getDayId() == null) {
                        response.put("success", false);
                        response.put("message", "Day is required");
                        return ResponseEntity.badRequest().body(response);
                }

                Day day = dayRepository.findById(data.getDayId()).orElse(null);
                if (day == null) {
                        response.put("success", false);
                        response.put("message", "Day not found");
                        return ResponseEntity.badRequest().body(response);
                }

                // validation timslot
                if (data.getTimeSlotId() == null) {
                        response.put("success", false);
                        response.put("message", "Time slot is required");
                        return ResponseEntity.badRequest().body(response);
                }

                TimeSlot timeSlot = timeSlotRepository.findById(data.getTimeSlotId()).orElse(null);
                if (timeSlot == null) {
                        response.put("success", false);
                        response.put("message", "Time slot not found");
                        return ResponseEntity.badRequest().body(response);
                }

                // validation room
                if (data.getRoomId() == null) {
                        response.put("success", false);
                        response.put("message", "Room is required");
                        return ResponseEntity.badRequest().body(response);
                }

                Room room = roomRepository.findById(data.getRoomId()).orElse(null);
                if (room == null) {
                        response.put("success", false);
                        response.put("message", "Room not found");
                        return ResponseEntity.badRequest().body(response);
                }

                AssignSubject assignSubject = new AssignSubject();
                assignSubject.setMajor(major);
                assignSubject.setTeacher(teacher);
                assignSubject.setSubject(subject);
                assignSubject.setDay(day);
                assignSubject.setTimeSlot(timeSlot);
                assignSubject.setRoom(room);
                assignSubject.setYear(year);
                assignSubject.setTerm(data.getTerm());
                assignSubject.setStatus(true);
                assignSubject.setCreatedAt(now);
                assignSubject.setCreatedBy(authUsername);

                assignSubjectRepository.save(assignSubject);

                response.put("success", true);
                response.put("message", "Assign Subject created successfully");
                response.put("data", assignSubject.getId());

                return ResponseEntity.ok(response);

        }
}
