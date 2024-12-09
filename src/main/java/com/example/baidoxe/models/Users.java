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
    @ToString.Exclude
    private Integer Id;
    private String HoTen;
    private Integer SDT;
    private String Email;
    private String Password;
    private String TaiKhoan;
    private Integer Status;
    private String Image;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "Role_Id", referencedColumnName = "Id")
    private Role role;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "NganHangId", referencedColumnName = "Id")
    private NganHang nganHang;

    @ToString.Exclude
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhuongTien> phuongTiens;

    public NganHang getNganHang() {
        return nganHang;
    }

    public void setNganHang(NganHang nganHang) {
        this.nganHang = nganHang;
    }
}