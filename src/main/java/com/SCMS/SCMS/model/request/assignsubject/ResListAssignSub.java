package com.SCMS.SCMS.model.request.assignsubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResListAssignSub {
    private Long id;
    private String studentName;
    private String teacherName;
    private String subjectName;
    private String term;
}
