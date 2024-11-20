package com.example.baidoxe.service;

import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.dto.UsersDTO;

import java.util.List;

public interface NganHangService {
    List<NganHangDTO> listActiveNganHang();
    NganHangDTO findNganHangById(Integer Id);
    NganHangDTO addNganHang(NganHangDTO nganHangDTO);
    NganHangDTO updateNganHang(NganHangDTO nganHangDTO);
    void deleteNganHang(Integer Id);
}


