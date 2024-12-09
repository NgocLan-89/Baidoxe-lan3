package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "ThongTinDo")
public class ThongTinDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer Id;
    private LocalDateTime ThoiGianVao;
    private LocalDateTime ThoiGianRa;
    private Integer Status;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "DatCho_Id", referencedColumnName = "Id")
    private DatCho datCho;
}
