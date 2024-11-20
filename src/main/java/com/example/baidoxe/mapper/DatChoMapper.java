package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.models.PhuongTien;
import com.example.baidoxe.models.ViTriDo;
import org.springframework.stereotype.Component;

@Component
public class DatChoMapper {
    public DatChoDTO toDatChoDTO(DatCho datCho){
        if (datCho == null){
            return null;
        }
        return DatChoDTO.builder()
                .Id(datCho.getId())
                .DangKyGioVao(datCho.getDangKyGioVao())
                .DangKyGioRa(datCho.getDangKyGioRa())
                .MaQR(datCho.getMaQR())
                .PhuongTien_Id(datCho.getPhuongTien() != null ? datCho.getPhuongTien().getId():null)
                .ViTriDo_Id(datCho.getViTriDo() != null ? datCho.getViTriDo().getId():null)
                .Status(datCho.getStatus())
                .build();
    }

    public DatCho toDatCho(DatChoDTO datChoDTO, PhuongTien phuongTien, ViTriDo viTriDo){
        if(datChoDTO == null){
            return null;
        }
        DatCho datCho = new DatCho();
        datCho.setId(datChoDTO.getId());
        datCho.setDangKyGioRa(datChoDTO.getDangKyGioRa());
        datCho.setDangKyGioVao(datChoDTO.getDangKyGioVao());
        datCho.setMaQR(datChoDTO.getMaQR());
        datCho.setStatus(datChoDTO.getStatus());
        datCho.setPhuongTien(phuongTien);
        datCho.setViTriDo(viTriDo);
        return datCho;
    }
    public DatCho addDatCho(DatChoDTO datChoDTO, PhuongTien phuongTien, ViTriDo viTriDo) {
        if (datChoDTO == null) {
            return null;
        }
        DatCho datCho = new DatCho();
        datCho.setDangKyGioVao(datChoDTO.getDangKyGioVao());
        datCho.setDangKyGioRa(datChoDTO.getDangKyGioRa());
        datCho.setMaQR(datChoDTO.getMaQR());
        datCho.setPhuongTien(phuongTien);
        datCho.setViTriDo(viTriDo);
        if (datChoDTO.getId() == null){
            datCho.setStatus(1);
        }else{
            datCho.setStatus(datCho.getStatus());
        }
        return datCho;
    }
}
