package com.example.baidoxe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaiDoDTO {
    private Integer Id;
    private String TenBaiDo;
    private String DiaChi;
    private Float KinhDo;
    private Float ViDo;
    private Integer Status;

    public BaiDoDTO(Integer Id, String TenBaiDo, String DiaChi, Integer Status, Float KinhDo,Float ViDo) {
        this.Id = Id;
        this.TenBaiDo = TenBaiDo;
        this.DiaChi = DiaChi;
        this.Status = Status;
        this.KinhDo=KinhDo;
        this.ViDo=ViDo;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTenBaiDo() {
        return TenBaiDo;
    }

    public void setTenBaiDo(String tenBaiDo) {
        TenBaiDo = tenBaiDo;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public Float getKinhDo() {
        return KinhDo;
    }

    public void setKinhDo(Float kinhDo) {
        KinhDo = kinhDo;
    }

    public Float getViDo() {
        return ViDo;
    }

    public void setViDo(Float viDo) {
        ViDo = viDo;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}

