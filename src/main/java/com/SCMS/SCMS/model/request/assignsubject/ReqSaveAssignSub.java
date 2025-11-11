package com.SCMS.SCMS.model.request.assignsubject;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqSaveAssignSub {
    
    private Long studentId;
    private Long teacherId;
    private Long majorId;
    private String role;
    private String term;
    private boolean status;
    private String createdBy;
    private Timestamp createdAt;
}
