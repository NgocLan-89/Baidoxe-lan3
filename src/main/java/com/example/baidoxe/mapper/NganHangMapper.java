package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.models.NganHang;
import org.springframework.stereotype.Component;

@Component
public class NganHangMapper {
    public NganHangDTO tonganHangDTO(NganHang nganHang) {
        if (nganHang == null) {
            return null;
        }
        return NganHangDTO.builder()
                .Id(nganHang.getId())
                .TenNganHang(nganHang.getTenNganHang())
                .SoTaiKhoan(nganHang.getSoTaiKhoan())
                .Status(nganHang.getStatus())
                .build();
    }

    public NganHang tonganHang(NganHangDTO nganHangDTO) {
        if (nganHangDTO == null) {
            return null;
        }
        NganHang nganHang = new NganHang();
        nganHang.setId(nganHangDTO.getId());
        nganHang.setTenNganHang(nganHangDTO.getTenNganHang());
        nganHang.setSoTaiKhoan(nganHangDTO.getSoTaiKhoan());
        nganHang.setStatus(nganHangDTO.getStatus());

        return nganHang;
    }
}