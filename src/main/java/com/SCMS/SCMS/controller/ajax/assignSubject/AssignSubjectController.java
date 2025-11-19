package com.SCMS.SCMS.controller.ajax.assignSubject;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SCMS.SCMS.entities.AssignSubject;
import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.assignsubject.AssignSubjectDto;
import com.SCMS.SCMS.model.request.assignsubject.ReqSaveAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ReqUpdateAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResListAssignSub;
import com.SCMS.SCMS.model.request.student.ReqSaveStudent;
import com.SCMS.SCMS.model.response.ApiResponse;
import com.SCMS.SCMS.model.response.DataResponse;
import com.SCMS.SCMS.model.subject.DayDto;
import com.SCMS.SCMS.model.subject.MajorDto;
import com.SCMS.SCMS.model.subject.PersonDto;
import com.SCMS.SCMS.model.subject.SubjectDto;
import com.SCMS.SCMS.model.subject.TeacherDto;
import com.SCMS.SCMS.model.subject.TimeSlotDto;
import com.SCMS.SCMS.service.assignsubject.AssignSubjectService;
import com.SCMS.SCMS.service.person.PersonService;
import com.SCMS.SCMS.service.subject.MajorService;

@RestController
@RequestMapping("/scms/admin-assignsubject")
public class AssignSubjectController {
   @Autowired
   private AssignSubjectService assignSubjectService;

   @Autowired
   private MajorService majorService;

   @GetMapping("/teacher")
   public List<TeacherDto> getTeacher() {
    return assignSubjectService.getTeacherDropdown();
   }

   @GetMapping("/getmajors")
   public List<MajorDto> getMajors() {
    return majorService.getAllMajor();
   }

   @GetMapping("/getsubjects")
   public List<SubjectDto> getSubjects() {
    return assignSubjectService.getAllSubjects();
   }

   @GetMapping("/getday")
   public List<DayDto> getDay() {
    return assignSubjectService.getAllDay();
   }

   @GetMapping("/gettimeslots")
   public List<TimeSlotDto> getTimeSlot () {
    return assignSubjectService.geetAllTimeSlot();
   }
}


