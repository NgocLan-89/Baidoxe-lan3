package com.example.baidoxe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class NganHangDTO {
    private Integer Id;
    private String TenNganHang;
    private String SoTaiKhoan;
    private Integer Status;
    private Integer User_Id;

    public NganHangDTO(Integer id, String tenNganHang, String soTaiKhoan, Integer status, Integer user_Id){
        this.Id = id;
        this.TenNganHang = tenNganHang;
        this.SoTaiKhoan = soTaiKhoan;
        this.Status =status;
        this.User_Id = user_Id;
    }
}