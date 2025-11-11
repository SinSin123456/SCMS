package com.SCMS.SCMS.model.request.assignsubject;

import com.SCMS.SCMS.entities.AssignSubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignSubjectDto {
    private Long id;
    private String studentName;
    private String teacherName;
    private String majorName;
    private String term;

    public AssignSubjectDto(AssignSubject entity) {
        this.id = entity.getId();
        this.studentName = entity.getStudent() != null ? entity.getStudent().getFullName() : null;
        this.teacherName = entity.getTeacher() != null ? entity.getTeacher().getFullName() : null;
        this.majorName = entity.getMajor() != null ? entity.getMajor().getMajorName() : null;
        this.term = entity.getTerm();
    }
}
