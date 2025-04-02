package com.example.vag.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String home(Authentication authentication, Model model) {
        // Add user details to model if needed
        return "index"; // Corresponds to /WEB-INF/views/home.html
    }
}