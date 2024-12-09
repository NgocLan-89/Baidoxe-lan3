package com.example.baidoxe.models;

import lombok.ToString;

import java.time.LocalDateTime;

public class ThanhToan {
    @ToString.Exclude
    private Long id;
    private LocalDateTime thoiGianVao;

    // Constructor
    public ThanhToan(Long id, LocalDateTime thoiGianVao) {
        this.id = id;
        this.thoiGianVao = thoiGianVao;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getThoiGianVao() {
        return thoiGianVao;
    }

    public void setThoiGianVao(LocalDateTime thoiGianVao) {
        this.thoiGianVao = thoiGianVao;
    }
}
