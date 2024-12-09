package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

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
    @ToString.Exclude
    private Integer Id;
    private String TenNganHang;
    private Integer Status;
    private String SoTaiKhoan;

    @ToString.Exclude
    @OneToMany(mappedBy = "nganHang", cascade = CascadeType.ALL)
    private Set<Users> users;
}