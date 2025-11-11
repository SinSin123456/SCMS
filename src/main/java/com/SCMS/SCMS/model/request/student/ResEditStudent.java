package com.SCMS.SCMS.model.request.student;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResEditStudent {
    private Long id;
    private String fullName;
   
    private String phone;
    private String sex;
    private Long classId;              
    private String yearName;          
    private List<Integer> majorIds;  
    private List<String> majorName; 
    private Timestamp registerDate;  
}
