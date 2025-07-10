package com.SCMS.SCMS.controller.ajax;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class testController {
    
    @GetMapping("/studentpage")
    public String getMethodName1() {
        return "Welcome student";
    }

    @GetMapping("/teacherpage")
    public String getMethodName2() {
        return "Welocome Teacher";
    }

    @GetMapping("/admin")
    public String getMethodName3() {
        return "Welcome Admin";
    }
    
}
