package com.example.baidoxe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhuongTienDTO {
    private Integer Id;
    private String LoaiPhuongTien;
    private String Hang;
    private String BienSo;
    private Integer Status;
    private Integer UserId;
}
