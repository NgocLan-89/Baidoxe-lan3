package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.models.BaiDo;
import org.springframework.stereotype.Component;

@Component
public class BaiDoMapper {
    public BaiDoDTO toBaiDoDTO(BaiDo baiDo) {
        if (baiDo == null) {
            return null;
        }
        return BaiDoDTO.builder()
                .Id(baiDo.getId())
                .TenBaiDo(baiDo.getTenBaiDo())
                .DiaChi(baiDo.getDiaChi())
                .Status(baiDo.getStatus())
                .KinhDo(baiDo.getKinhDo())
                .ViDo(baiDo.getViDo())
                .build();
    }

    public BaiDo toBaiDo(BaiDoDTO baiDoDTO) {
        if (baiDoDTO == null) {
            return null;
        }
        BaiDo baiDo = new BaiDo();
        baiDo.setId(baiDoDTO.getId());
        baiDo.setTenBaiDo(baiDoDTO.getTenBaiDo());
        baiDo.setDiaChi(baiDoDTO.getDiaChi());
        baiDo.setStatus(baiDoDTO.getStatus());
        baiDo.setKinhDo(baiDoDTO.getKinhDo());
        baiDo.setViDo(baiDoDTO.getViDo());
        return baiDo;
    }
}
