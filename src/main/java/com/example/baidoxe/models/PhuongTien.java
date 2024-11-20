package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer Id;
    private String LoaiPhuongTien;
    private String Hang;
    private String BienSo;

    private Integer Status;

    @OneToMany(mappedBy = "phuongTien", cascade = CascadeType.ALL)
    private Set<DatCho> datCho;

    @ManyToOne
    @JoinColumn(name = "User_Id", referencedColumnName = "Id")
    private Users users;
}
