package com.SCMS.SCMS.model.request.assignsubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResListAssignSub {
    private String teacherName;
    private String majorName;
    private String subjectName;
    private String dayName;
    private String timeSlotName;
    private String roomName;
    private String term;
}
