package com.SCMS.SCMS.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminRegisterRequest {
    // public AdminRegisterRequest(String username, String password, String confirmPassword, String roles) {
    //     this.username = username;
    //     this.password = password;
    //     this.confirmPassword = confirmPassword;
    //     this.roles = roles;
    // }
    private String username;
    private String password;
    private String confirmPassword;
    private String roles;
}
