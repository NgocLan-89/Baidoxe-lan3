package com.example.baidoxe.controller;

import com.example.baidoxe.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/baocao")
public class BaocaoController {

    @Autowired
    private ThanhToanService thanhToanService;

    @GetMapping("/thongke")
    public String getThongKe(Model model) {
        List<Object[]> bookingsData = thanhToanService.countBookingsByYearAndMonth();


        // Dữ liệu biểu đồ
        model.addAttribute("bookingsData", bookingsData);

        return "bao-cao";
    }

}
