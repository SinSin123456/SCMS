package com.SCMS.SCMS.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResListUsers {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String roles;
    private String joinDate; 
}
