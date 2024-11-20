package com.example.baidoxe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/KH")
public class KhController {
    @GetMapping("/trogiup")
    public String showTroGiup() {
        return "KH/trogiup";
    }
    @GetMapping("/gopy")
    public String showGopY() {
        return "KH/gopy";
    }
}

