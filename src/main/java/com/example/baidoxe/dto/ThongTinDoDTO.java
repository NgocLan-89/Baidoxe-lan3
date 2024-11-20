package com.example.baidoxe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ThongTinDoDTO {
    private Integer Id;
    private LocalDateTime ThoiGianVao;
    private LocalDateTime ThoiGianRa;
    private Integer DatCho_Id;
    private Integer Status;
    private String HoTen;
    private String Email;
    private String TenBaiDo;
    private Integer ChiTietViTri;
    private String TenNganHang;
    private String SoTaiKoan;
    private String BienSo;
    private Integer BaiDo_Id;
    private Integer PhuongTien_Id;
    private Integer NganHang_Id;
    private Integer User_Id;
    private Integer ViTriDo_Id;


}

