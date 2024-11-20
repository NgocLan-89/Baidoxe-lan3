package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.ViTriDoDTO;
import com.example.baidoxe.models.BaiDo;
import com.example.baidoxe.models.ViTriDo;
import org.springframework.stereotype.Component;

@Component
public class ViTriDoMapper {

    public ViTriDoDTO toViTriDoDTO(ViTriDo viTriDo) {
        if (viTriDo == null) {
            return null;
        }
        return ViTriDoDTO.builder()
                .Id(viTriDo.getId())
                .BaiDo_Id(viTriDo.getBaiDo() != null ? viTriDo.getBaiDo().getId() : null) // Lấy ID của BaiDo nếu không null
                .TenBaiDo(viTriDo.getBaiDo() != null ? viTriDo.getBaiDo().getTenBaiDo() : null) // Lấy TenBaiDo từ BaiDo
                .ChiTietViTri(viTriDo.getChiTietViTri())
                .Status(viTriDo.getStatus())
                .build();
    }

    public ViTriDo toViTriDo(ViTriDoDTO viTriDoDTO, BaiDo baiDo) {
        if (viTriDoDTO == null) {
            return null;
        }
        ViTriDo viTriDo = new ViTriDo();
        viTriDo.setId(viTriDoDTO.getId());
        viTriDo.setChiTietViTri(viTriDoDTO.getChiTietViTri());
        viTriDo.setStatus(viTriDoDTO.getStatus());
        viTriDo.setBaiDo(baiDo); // Thiết lập thực thể BaiDo
        return viTriDo;
    }
}
