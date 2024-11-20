package com.example.baidoxe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {
    private Integer Id;
    private String HoTen;
    private Integer SDT;
    private String Email;
    private String Password;
    private String TaiKhoan;
    private String  SoTaiKhoan;
    private Integer Status;
    private String Image;
    private Integer NganHangId;
    private String TenNganHang;
    private Integer Role_Id;
    private String Role_name;


    // Add this constructor to match the query parameters
    public UsersDTO(Integer id, String hoTen, Integer sDT, String email, String taiKhoan, String soTaiKhoan,
                    Integer status, String password, String image,
                    Integer nganHangId, String tenNganHang, Integer roleId, String roleName) {
        this.Id = id;
        this.HoTen = hoTen;
        this.SDT = sDT;
        this.Email = email;
        this.Password = password;
        this.TaiKhoan = taiKhoan;
        this.SoTaiKhoan = soTaiKhoan;
        this.Status = status;
        this.Image = image;
        this.NganHangId = nganHangId;
        this.TenNganHang = tenNganHang;
        this.Role_Id = roleId;
        this.Role_name = roleName;
    }

}