package com.SCMS.SCMS.model.request.assignsubject;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdateAssignSub {
    private Long id; 
    private Long studentId;
    private Long teacherId;
    private Long subjectId;
    private String role;
    private String term;
    private boolean status;
    private String updatedBy;
    private Timestamp updatedAt;
}
