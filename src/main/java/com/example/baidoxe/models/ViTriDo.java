package com.example.baidoxe.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "ViTriDo")
public class ViTriDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer Id;
    private Integer Status;
    private Integer ChiTietViTri;
    @ToString.Exclude
    @OneToMany(mappedBy = "viTriDo",  cascade = CascadeType.ALL)
    private Set<DatCho> datChos;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "BaiDo_Id", referencedColumnName = "Id")
    private BaiDo baiDo;
}
