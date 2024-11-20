package com.example.baidoxe.repository;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.models.BaiDo;
import com.example.baidoxe.models.NganHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NganHangRepository extends JpaRepository<NganHang, Integer> {
    Optional<NganHang> findById(Integer Id);

    @Query("SELECT ng FROM NganHang ng WHERE ng.Status = 1")
    List<NganHangDTO> findActiveNganHang(@Param("Status") Integer Status);
}