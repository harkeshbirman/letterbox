package com.harkesh.letterbox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String getWelcomePage() {
        return "index.html";
    }
}
