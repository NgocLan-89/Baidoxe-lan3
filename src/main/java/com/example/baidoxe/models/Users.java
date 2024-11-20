package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class Users {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String HoTen;
    private Integer SDT;
    private String Email;
    private String Password;
    private String TaiKhoan;
    private Integer Status;
    private String Image;

    @ManyToOne
    @JoinColumn(name = "Role_Id", referencedColumnName = "Id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "NganHangId", referencedColumnName = "Id")
    private NganHang nganHang;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhuongTien> phuongTiens;

    public NganHang getNganHang() {
        return nganHang;
    }

    public void setNganHang(NganHang nganHang) {
        this.nganHang = nganHang;
    }
}