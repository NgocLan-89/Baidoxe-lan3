package com.example.baidoxe.service;

import com.example.baidoxe.dto.PhuongTienDTO;
import java.util.List;

public interface PhuongTienService {
    List<PhuongTienDTO> findByUserId(Integer userId);
    PhuongTienDTO findById(Integer id, Integer userId);
    PhuongTienDTO addPhuongTien(PhuongTienDTO phuongTienDTO);
    PhuongTienDTO updatePhuongTien(PhuongTienDTO phuongTienDTO);
    void deletePhuongTien(Integer id, Integer userId);
    List<PhuongTienDTO> findPhuongTienUser(Integer userId);
}
