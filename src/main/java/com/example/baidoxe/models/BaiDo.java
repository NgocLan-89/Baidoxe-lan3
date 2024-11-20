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
@Table(name = "BaiDo")
public class BaiDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String TenBaiDo;
    private String DiaChi;
    private Integer Status;
    private Float KinhDo;
    private Float ViDo;

    @OneToMany(mappedBy = "baiDo",  cascade = CascadeType.ALL)
    private Set<ViTriDo> viTriDos;

}
