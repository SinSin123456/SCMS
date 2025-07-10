package com.SCMS.SCMS.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;

   
}
