package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "NganHang")
public class NganHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String TenNganHang;
    private Integer Status;
    private String SoTaiKhoan;

    @OneToMany(mappedBy = "nganHang", cascade = CascadeType.ALL)
    private Set<Users> users;
}