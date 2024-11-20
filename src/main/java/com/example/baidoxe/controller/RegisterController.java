package com.example.baidoxe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @GetMapping("/dangki")
    public String showForm(Model model) {
        // Thêm bất kỳ dữ liệu nào vào model nếu cần
        model.addAttribute("message", "Chào mừng bạn đến với form của chúng tôi!");
        return "register"; // Tên của file HTML (form.html)
    }
}
