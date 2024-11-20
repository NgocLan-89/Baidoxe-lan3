package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.ThongTinDoDTO;
import com.example.baidoxe.models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ThongTinDoMapper {

    public ThongTinDo toEntity(ThongTinDoDTO thongTinDoDTO, DatCho datCho) {
        if (thongTinDoDTO == null || datCho == null) {
            return null;
        }

        return ThongTinDo.builder()
                .ThoiGianVao(thongTinDoDTO.getThoiGianVao())
                .ThoiGianRa(thongTinDoDTO.getThoiGianRa())
                .Status(thongTinDoDTO.getStatus())
                .datCho(datCho) // Gắn DatCho vào ThongTinDo
                .build();
    }

    public ThongTinDoDTO toDTO(ThongTinDo thongTinDo) {
        if (thongTinDo == null) {
            return null;
        }

        ThongTinDoDTO thongTinDoDTO = new ThongTinDoDTO();
        thongTinDoDTO.setId(thongTinDo.getId());
        thongTinDoDTO.setThoiGianVao(thongTinDo.getThoiGianVao());
        thongTinDoDTO.setThoiGianRa(thongTinDo.getThoiGianRa());
        thongTinDoDTO.setStatus(thongTinDo.getStatus());
        thongTinDoDTO.setDatCho_Id(thongTinDo.getDatCho().getId()); // Truyền DatCho_Id
        return thongTinDoDTO;
    }
}