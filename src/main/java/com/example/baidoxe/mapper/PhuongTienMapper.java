package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.PhuongTienDTO;
import com.example.baidoxe.models.PhuongTien;
import com.example.baidoxe.models.Users;
import org.springframework.stereotype.Component;

@Component
public class PhuongTienMapper {

    // Chuyển đổi từ PhuongTien entity sang PhuongTienDTO
    public PhuongTienDTO toPhuongTienDTO(PhuongTien phuongTien) {
        PhuongTienDTO dto = new PhuongTienDTO();
        dto.setId(phuongTien.getId());
        dto.setLoaiPhuongTien(phuongTien.getLoaiPhuongTien());
        dto.setHang(phuongTien.getHang());
        dto.setBienSo(phuongTien.getBienSo());
        dto.setStatus(phuongTien.getStatus());
        dto.setUserId(phuongTien.getUsers().getId());
        return dto;
    }

    // Chuyển đổi từ PhuongTienDTO sang PhuongTien entity
    public PhuongTien toPhuongTien(PhuongTienDTO dto) {
        PhuongTien phuongTien = new PhuongTien();
        phuongTien.setId(dto.getId());
        phuongTien.setLoaiPhuongTien(dto.getLoaiPhuongTien());
        phuongTien.setHang(dto.getHang());
        phuongTien.setBienSo(dto.getBienSo());
        phuongTien.setStatus(dto.getStatus());

        // Thiết lập User từ UserId
        Users user = new Users();
        user.setId(dto.getUserId());
        phuongTien.setUsers(user);

        return phuongTien;
    }
}
