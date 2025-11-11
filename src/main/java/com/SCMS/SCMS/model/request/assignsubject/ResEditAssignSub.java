package com.SCMS.SCMS.model.request.assignsubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResEditAssignSub {
    private Long personId;
    private String personName;
    private Long majorId;
    private String majorName;
    private String role;
    private String term;
}
