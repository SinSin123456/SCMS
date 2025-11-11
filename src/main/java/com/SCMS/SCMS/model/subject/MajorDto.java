package com.SCMS.SCMS.model.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorDto {
    private Long id; 
    private String majorName;
}
