package com.example.baidoxe.controller;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.service.DatChoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/datcho")
public class DatChoController {
    @Autowired
    private DatChoService datChoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/list")
    public String listBaiDo(Model model) {
        List<DatChoDTO> datChoDTOS = datChoService.datChoList();
        model.addAttribute("listDatCho", datChoDTOS);
        return "map/datcho-list";
    }

}
