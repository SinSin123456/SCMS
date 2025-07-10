package com.SCMS.SCMS.controller.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.SCMS.SCMS.annotation.WebController;

@WebController
public class AdminFragmentController {
    @GetMapping("/admin/fragment/users")
    public String usersFragment(Model model) {
        return "layout/admin/fragment/users :: content";
    }
}
