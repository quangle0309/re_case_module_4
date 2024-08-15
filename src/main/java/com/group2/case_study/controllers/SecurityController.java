package com.group2.case_study.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", defaultValue = "") String error) {
        if (!error.isEmpty()) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
        }
        return "login/login";
    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "redirect:/flight/home";
    }

    @GetMapping("/flight/home")
    public String homePage(Model model) {
        model.addAttribute("title", "Home");
        return "flight/home";
    }
}
