package com.SCMS.SCMS.controller.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.SCMS.SCMS.annotation.WebController;
import com.SCMS.SCMS.hepler.View;

@WebController
public class TUsersController {
    @GetMapping("admin/users")
    public String userPage(Model model) {
        return View.USERS;
    }
}
