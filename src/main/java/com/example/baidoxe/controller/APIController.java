package com.example.baidoxe.controller;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.repository.BaiDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class APIController {
    @Autowired
    private BaiDoRepository baiDoRepository;
    @GetMapping("/baido/api")
    @ResponseBody
    public List<BaiDoDTO> getAllBaiDos() {
        return baiDoRepository.findActiveBaiDo();
    }
}
