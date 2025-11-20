package com.SCMS.SCMS.model.request.assignsubject;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqSaveAssignSub {
    private Long id;
    private Long teacherId;
    private Long roomId;
    private Long userId;
    private Long dayId;
    private Long timeSlotId;
    private Long subjectId;
    private Long yearId;
    private Long majorId;
    private String term;
    private boolean status;
    private String createdBy;
    private Timestamp createdAt;
}
