package com.SCMS.SCMS.controller.ajax.student;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.request.student.ReqSaveStudent;
import com.SCMS.SCMS.model.request.student.ReqUpdateStudent;
import com.SCMS.SCMS.model.request.student.ResEditStudent;
import com.SCMS.SCMS.model.request.student.ResListStudent;
import com.SCMS.SCMS.model.request.student.StudentDto;
import com.SCMS.SCMS.model.response.DataResponse;
import com.SCMS.SCMS.model.subject.MajorDto;
import com.SCMS.SCMS.model.subject.PersonDto;
import com.SCMS.SCMS.model.subject.YearDto;
import com.SCMS.SCMS.service.student.StudentMangementService;
import com.SCMS.SCMS.service.subject.MajorService;
import com.SCMS.SCMS.service.subject.YearService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@Transactional
@RequestMapping("/scms/admin-student")
public class StudentMangementController {
    @Autowired
    private StudentMangementService studentMangementService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private YearService yearService;

    @PostMapping("/addstudent")
    public ResponseEntity<Map<String, Object>> addStudent(@RequestBody ReqSaveStudent data) {
        return studentMangementService.addStudent(data);
    }

    @PostMapping("/studenttable")
    public ResDatatableParam<ResListStudent> getAllstudent(@RequestBody ReqDatatableParam data) {
        return studentMangementService.getStudentsList(data);
    }

    @GetMapping("/editstudent/{id}")
    public ResponseEntity<ResEditStudent> editStudent(@PathVariable("id") Long id) {
        return studentMangementService.editStudent(id);
    }

    @PostMapping("/deletestudent/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        studentMangementService.deletestudent(id);
        return ResponseEntity.ok(DataResponse.success(null, "Student deleted successfully"));
    }

    @PostMapping("/updatestudent")
    public ResponseEntity<Map<String, Object>> updateStudent(@RequestBody ReqUpdateStudent data) {
        return studentMangementService.updateStudent(data);
    }

    @GetMapping("/students")
    public List<StudentDto> getAllStudent() {
        return studentMangementService.getStudentDropdown();
    }

    @GetMapping("/majors")
    public List<MajorDto> getMajors() {
        return majorService.getAllMajor();
    }

    @GetMapping("/year")
    public List<YearDto> getYears() {
        return yearService.getAllYear();
    }
}
