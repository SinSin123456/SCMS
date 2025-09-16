package com.SCMS.SCMS.model.request.admin;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUdateUsers {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String roles;
    private String joinDate;
    private boolean status;
    private String updatedBy;
    private Timestamp updatedAt;
}
