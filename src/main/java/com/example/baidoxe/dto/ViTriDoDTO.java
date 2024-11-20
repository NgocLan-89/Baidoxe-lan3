package com.example.baidoxe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViTriDoDTO {
    private Integer Id;
    private Integer BaiDo_Id;
    private Integer Status;
    private Integer ChiTietViTri;
    private String TenBaiDo;
}
