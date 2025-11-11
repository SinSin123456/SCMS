package com.SCMS.SCMS.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SCMS.SCMS.annotation.WebController;

@Controller
@RequestMapping("/admin1")
public class AdminFragmentController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "layout/admin/admin-dashboard";
    }

    @GetMapping("/users/register")
    public String registerUserForm() {
        return "layout/admin/users";
    }

    @GetMapping("/students/management")
    public String maganagestudent() {
        return "layout/admin/students";
    }

    @GetMapping("/subject/assingsubjects")
    public String assingsubjects() {
        return "layout/admin/assignsubjects";
    }

    @GetMapping("/admin/timetable")
    public String timetable() {
        return "layout/admin/timetable";
    }

    @GetMapping("/admin/leave")
    public String leave() {
        return "layout/admin/leave";
    }

    @GetMapping("/admin/attendance")
    public String attendance() {
        return "layout/admin/attendance";
    }

    @GetMapping("/admin/announcements")
    public String announcements() {
        return "layout/admin/announcements";
    }

    @GetMapping("/admin/reports")
    public String reports() {
        return "layout/admin/reports";
    }
}
