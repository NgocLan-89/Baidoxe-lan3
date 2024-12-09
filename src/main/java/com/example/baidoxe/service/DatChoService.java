package com.example.baidoxe.service;

import com.example.baidoxe.dto.DatChoDTO;

import java.util.List;

public interface DatChoService {
    DatChoDTO finDatChoById(Integer Id);
    DatChoDTO addDatCho(DatChoDTO datChoDTO);
    List<DatChoDTO> datChoList();
    void createNewReservation(DatChoDTO datChoDTO);
    void extendExpiredReservations();
}
