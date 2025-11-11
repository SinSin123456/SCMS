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
import com.SCMS.SCMS.model.subject.MajorDto;
import com.SCMS.SCMS.model.subject.PersonDto;

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

    @Autowired
    private PersonService personService;

    @PostMapping("/addAssignSub")
    public Map<String, Object> addAssignSub(@RequestBody ReqSaveAssignSub data) {
        try {
            AssignSubjectDto saved = assignSubjectService.addAssignSubject(data);
            return DataResponse.success(saved, "Assign Subject saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.failed(null, e.getMessage());
        }
    }

    @PostMapping("/subjects")
    public List<MajorDto> getMajors() {
        return majorService.getAllMajor();
    }

    @PostMapping("/persons")
    public List<PersonDto> getPerson() {
        return personService.getAllPersons();
    }

    @PostMapping("/listAssignSubjects")
    public ResDatatableParam<ResListAssignSub> listAssignSubjects(@RequestBody ReqDatatableParam data) {
        return assignSubjectService.getAssignSubjectsList(data);
    }

    @GetMapping("/editassign/{id}")
    public ResponseEntity<ResEditAssignSub> editAssignSub (@PathVariable("id") Long id) {
        return assignSubjectService.editAssignSub(id);
    }

    @PostMapping("/updateassignsub")
    public ResponseEntity<?> index(@RequestBody ReqUpdateAssignSub data) {
        try {
            AssignSubjectDto updated = assignSubjectService.updateAssignSubject(data);
           return ResponseEntity.ok(DataResponse.success(updated, "updated"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/deleteassignsub/{id}")
    public ResponseEntity<?> deleteAssing(@PathVariable("id") Long id) {
        AssignSubjectDto assignSubject = assignSubjectService.deleteAssign(id);
        return ResponseEntity.ok(DataResponse.success(assignSubject, "deleted"));
    }

}

