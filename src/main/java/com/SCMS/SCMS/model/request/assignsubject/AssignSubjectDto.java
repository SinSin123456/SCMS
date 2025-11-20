package com.SCMS.SCMS.model.request.assignsubject;

import java.sql.Timestamp;

import com.SCMS.SCMS.entities.AssignSubject;
import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.entities.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignSubjectDto {
    private Long id;

    private Long majorId;
    private String majorName;

    private Long teacherId;
    private String teacherName;

    private Long subjectId;
    private String subjectName;

    private Long dayId;
    private String dayName;

    private Long timeSlodId;
    private String timeSlotName;

    private Long roomId;
    private String roomName;

    private Long termId;
    private String termName;

    private Long yearId;
    private String yearName;

    private boolean status;

    private String createdBy;
    private Timestamp createdAt;

    private String updatedBy;
    private Timestamp updatedAt;

    private String deletedBy;
    private Timestamp deletedAt;
}
