package com.example.baidoxe.service;

import com.example.baidoxe.dto.ThanhToanDTO;
import com.example.baidoxe.dto.ThongTinDoDTO;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.models.ThongTinDo;

import java.util.List;

public interface ThongTinDoService {

    List<ThongTinDoDTO> getAllThongTinDo(); // Phương thức cũ

    List<ThongTinDoDTO> getThongTinDoByUserAndStatus(int userId, int status); // Thêm phương thức này vào


    void addThongTinDo(ThongTinDoDTO thongTinDoDTO);
}
