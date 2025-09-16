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
public class ReqUpdateStudent {
    private Long id;               
    private String fullName;       
    private String email;          
    private String phone;         
    private String sex;            
    private String className;       

    private List<String> subjectNames;
    private Timestamp registerDate;   
    private boolean status;
    private Timestamp updatedAt;
    private String updatedBy;
}
