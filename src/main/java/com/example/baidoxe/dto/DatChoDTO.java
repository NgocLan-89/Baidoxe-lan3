package com.example.baidoxe.dto;

import com.example.baidoxe.models.ViTriDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatChoDTO {
    private Integer Id;
    private LocalDateTime DangKyGioVao;
    private LocalDateTime DangKyGioRa;
    private Integer Status;
    private String MaQR;
    private Integer ViTriDo_Id;
    private Integer PhuongTien_Id;

}
