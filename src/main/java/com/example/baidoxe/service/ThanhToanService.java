package com.example.baidoxe.service;

import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.dto.ThanhToanDTO;
import com.example.baidoxe.dto.ThongTinDoDTO;

import java.util.List;

public interface ThanhToanService {
    List<ThanhToanDTO> getThanhToanbyPTID();
    ThanhToanDTO finThanhToanById(Integer Id);
    List<ThanhToanDTO> findThanhToan();

    List<Object[]> countBookingsByYearAndMonth();

    List<Object[]> countBookingsByMonth();
}
