package com.example.baidoxe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class BaocaoController {
    @GetMapping("/baocaothongke")
    public String showBaocao() {
        return "baocao-thongke";
    }
}
