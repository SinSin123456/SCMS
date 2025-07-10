package com.SCMS.SCMS.model.request;

import java.util.Set;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private Set<String> roles;
}
