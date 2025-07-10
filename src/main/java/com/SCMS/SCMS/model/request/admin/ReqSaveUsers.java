package com.SCMS.SCMS.model.request.admin;



import java.sql.Timestamp;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor

public class ReqSaveUsers {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String roles;
    private String joinDate;
    private String password;
    private String createdBy;
    private Timestamp createdAt;
}
