package com.SCMS.SCMS.controller.ajax;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCMS.SCMS.entities.Login;
import com.SCMS.SCMS.model.request.AuthRequest;
import com.SCMS.SCMS.model.response.AuthResponse;
import com.SCMS.SCMS.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    
    @Autowired
    private AuthService authService;

    @PostMapping({"", "/", "/d"})
    public String postMethodName(@RequestBody String entity) {
        return "HI";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registe(@RequestBody AuthRequest request) {
        Login user = authService.register(request.getUsername(), request.getPassword(), request.getRoles());
        return ResponseEntity.ok(user);
    }

    
    @PostMapping("/login-page")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

