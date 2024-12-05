package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(precision = 9, scale = 3) // DECIMAL(10, 2) trong database
    private BigDecimal SoTien;

    @ManyToOne
    @JoinColumn(name = "VitriDo_Id", referencedColumnName = "Id")
    private ViTriDo viTriDo;

    @ManyToOne
    @JoinColumn(name = "PhuongTien_Id", referencedColumnName = "Id")
    private PhuongTien phuongTien;

    @OneToMany(mappedBy = "datCho", cascade = CascadeType.ALL)
    private Set<ThongTinDo> thongTinDos;
}