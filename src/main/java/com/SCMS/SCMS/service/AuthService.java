package com.SCMS.SCMS.service;

import java.sql.Timestamp;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.entities.Login;
import com.SCMS.SCMS.repository.LoginRepository;
import com.SCMS.SCMS.utils.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private LoginRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Login register(String username, String password, Set<String> roles) {
        Login user = new Login();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        Login user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(username, user.getRoles());
    }

}
