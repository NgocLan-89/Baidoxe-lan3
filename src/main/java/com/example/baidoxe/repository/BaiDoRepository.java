package com.example.baidoxe.repository;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.models.BaiDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BaiDoRepository extends JpaRepository<BaiDo, Integer> {
    Optional<BaiDo> findById(Integer Id);

    @Query("SELECT new com.example.baidoxe.dto.BaiDoDTO(" +
            "b.Id, b.TenBaiDo, b.DiaChi, b.KinhDo, b.ViDo,b.Status) " +
            "FROM BaiDo b " +
            "WHERE b.Status = 1")
    List<BaiDoDTO> findActiveBaiDo();

}