package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DatCho")
public class DatCho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer Id;
    private LocalDateTime DangKyGioVao;
    private LocalDateTime DangKyGioRa;
    private Integer Status;
    private String MaQR;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "VitriDo_Id", referencedColumnName = "Id")
    private ViTriDo viTriDo;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PhuongTien_Id", referencedColumnName = "Id")
    private PhuongTien phuongTien;
    @ToString.Exclude
    @OneToMany(mappedBy = "datCho", cascade = CascadeType.ALL)
    private Set<ThongTinDo> thongTinDos;

}