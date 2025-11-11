package com.SCMS.SCMS.model.request.student;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSaveStudent {
    private Long userId;
    private String fullName;

    private String phone;
    private String sex;
    private Long yearId;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> majorName;
    private Timestamp registerDate;
    private boolean status;
    private Timestamp createdAt;
    private String createdBy;
}
