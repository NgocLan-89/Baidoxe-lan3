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
public class ThanhToanDTO {
    private Integer Id;
    private LocalDateTime DangKyGioVao;
    private LocalDateTime DangKyGioRa;
    private Integer Status;
    private Integer ViTriDo_Id;
    private String MaQR;
    private Integer PhuongTien_Id;
    private String LoaiPhuongTien;
    private String BienSo;
    private String TenNganHang;
    private Integer NganHang_Id;
    private Integer User_Id;
    private String SoTaiKhoan;
    private Integer ChiTietViTri;
    private String TenBaiDo;
    private String DiaChi;
    private Integer BaiDo_Id;

    // Nếu cần thiết một constructor đầy đủ các trường
    public ThanhToanDTO(Integer id, LocalDateTime dangKyGioVao, LocalDateTime dangKyGioRa, Integer status,
                        Integer viTriDo_Id, String maQR, Integer phuongTien_Id, String loaiPhuongTien,
                        String bienSo, String tenNganHang, String soTaiKhoan, Integer chiTietViTri,
                        String tenBaiDo, String diaChi, Integer baiDo_Id, Integer user_Id, Integer nganHang_Id) {
        this.Id = id;
        this.DangKyGioVao = dangKyGioVao;
        this.DangKyGioRa = dangKyGioRa;
        this.Status = status;
        this.ViTriDo_Id = viTriDo_Id;
        this.MaQR = maQR;
        this.PhuongTien_Id = phuongTien_Id;
        this.LoaiPhuongTien = loaiPhuongTien;
        this.BienSo = bienSo;
        this.TenNganHang = tenNganHang;
        this.SoTaiKhoan = soTaiKhoan;
        this.ChiTietViTri = chiTietViTri;
        this.TenBaiDo = tenBaiDo;
        this.DiaChi = diaChi;
        this.BaiDo_Id = baiDo_Id;
        this.User_Id = user_Id;
        this.NganHang_Id = nganHang_Id;
    }

}
