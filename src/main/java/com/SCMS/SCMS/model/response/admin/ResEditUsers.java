package com.SCMS.SCMS.model.response.admin;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResEditUsers {
    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String roles;
    private String joinDate;

    public ResEditUsers(Long id, String fullname, String username, String email, Set<String> roles,
            Timestamp joinDate) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.roles = roles != null ? roles.stream().collect(Collectors.joining(",")) : "";
        this.joinDate = joinDate != null ? joinDate.toString().substring(0, 10) : null;
    }
}
