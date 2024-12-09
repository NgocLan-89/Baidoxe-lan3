package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "PhuongTien")
public class PhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer Id;
    private String LoaiPhuongTien;
    private String Hang;
    private String BienSo;

    private Integer Status;

    @ToString.Exclude
    @OneToMany(mappedBy = "phuongTien", cascade = CascadeType.ALL)
    private Set<DatCho> datCho;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "User_Id", referencedColumnName = "Id")
    private Users users;
}
