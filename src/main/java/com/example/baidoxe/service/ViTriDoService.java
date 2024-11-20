package com.example.baidoxe.service;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.dto.UsersDTO;
import com.example.baidoxe.dto.ViTriDoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ViTriDoService {
    List<ViTriDoDTO> listActiveViTriDo();
    int countByStatus();
    ViTriDoDTO addViTriDo(ViTriDoDTO viTriDoDTO);
    ViTriDoDTO updateViTriDo(Integer id, ViTriDoDTO viTriDoDTO);
    ViTriDoDTO deleteViTriDo(Integer id);
    ViTriDoDTO findViTriDoById(Integer Id);
    List<ViTriDoDTO> getActiveViTriDoByBaiDoId(Integer baiDoId);
    ViTriDoDTO ApdateStatus(Integer Id);
    int countActiveViTriDoByBaiDoId(Integer baiDoId);
}
