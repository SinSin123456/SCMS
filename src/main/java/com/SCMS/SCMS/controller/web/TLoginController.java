package com.SCMS.SCMS.controller.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.SCMS.SCMS.entities.Login;
import com.SCMS.SCMS.hepler.View;
import com.SCMS.SCMS.model.request.AdminRegisterRequest;
import com.SCMS.SCMS.model.request.AuthRequest;
import com.SCMS.SCMS.repository.LoginRepository;

@Controller
// @RequestMapping("/login")
public class TLoginController {

    @Autowired
    private LoginRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String index(Model model) {
        return View.INDEX;
    }

    @GetMapping("/login-page") // changed from "/login"
    public String showLogin(Model model, @RequestParam(required = false) String roles) {
        model.addAttribute("role", roles);
        model.addAttribute("authRequest", new AuthRequest());
        return View.LOGIN;
    }

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("authRequest", new AuthRequest());
        model.addAttribute("role", "ADMIN");
        return View.LOGIN; // or the correct view for your admin login page
    }

    // @GetMapping("/admin/register")
    // public String adminRegister(@RequestParam(required = false) String roles,
    // Model model) {
    // model.addAttribute("selectedRole", roles); // ADD THIS
    // model.addAttribute("authRequest", new AuthRequest()); // ADD THIS
    // return View.ADMIN_REGISTER; // should match your Thymeleaf view name
    // }

    @GetMapping("/admin/register")
    public String adminRegister(@RequestParam(required = false) String roles, Model model) {
        System.out.println(">>> /admin/register GET called with roles=" + roles);
        model.addAttribute("selectedRole", roles);
        AdminRegisterRequest req = new AdminRegisterRequest();
        req.setRoles(roles); // Only set the role
        model.addAttribute("adminRegisterRequest", req);
        return View.ADMIN_SIGN_UP;
    }

    @PostMapping("/admin/register")
    public String processRegister(
            @ModelAttribute("adminRegisterRequest") AdminRegisterRequest adminRegisterRequest,
            Model model) {
        // Example validation
        if (adminRegisterRequest.getUsername() == null || adminRegisterRequest.getUsername().isEmpty()) {
            model.addAttribute("error", "Username is required");
            return View.ADMIN_SIGN_UP;
        }
        if (!adminRegisterRequest.getPassword().equals(adminRegisterRequest.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return View.ADMIN_SIGN_UP;
        }
        // Registration logic here...
        Login user = new Login();
        user.setUsername(adminRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(adminRegisterRequest.getPassword()));
        user.setRoles(Set.of(adminRegisterRequest.getRoles())); // or Set.of(role) if single role

        userRepository.save(user);

        String roles = adminRegisterRequest.getRoles();
        if ("ADMIN".equalsIgnoreCase(roles)) {
            return "redirect:/login-page?roles=ADMIN";
        } else if ("TEACHER".equalsIgnoreCase(roles)) {
            return "redirect:/login-page?roles=TEACHER";
        } else if ("STUDENT".equalsIgnoreCase(roles)) {
            return "redirect:/login-page?roles=STUDENT";
        }
        return "redirect:/login-page";
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model) {
        // Get the username of the logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Pass it to the view
        model.addAttribute("username", username);

        return View.STUDENT_DASHBOARD;
    }

    @GetMapping("/teacher/dashboard")
    public String teacherDashboard(Model model) {
        // Get the username of the logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Pass it to the view
        model.addAttribute("username", username);

        return View.TEACHER_DASHBOARD;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        return View.ADMIN_DASHBOARD;
    }
}