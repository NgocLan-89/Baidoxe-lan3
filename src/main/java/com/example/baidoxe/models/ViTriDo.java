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
@Table(name = "ViTriDo")
public class ViTriDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer Status;
    private Integer ChiTietViTri;
    @OneToMany(mappedBy = "viTriDo",  cascade = CascadeType.ALL)
    private Set<DatCho> datChos;

    @ManyToOne
    @JoinColumn(name = "BaiDo_Id", referencedColumnName = "Id")
    private BaiDo baiDo;
}
