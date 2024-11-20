package com.example.baidoxe.repository;

import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.models.DatCho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatChoRepository extends JpaRepository<DatCho, Integer> {
    Optional<DatCho> findById(Integer Id);

    @Query("SELECT dc FROM DatCho dc WHERE dc.Status = 1")
    List<DatChoDTO> findAll(@Param("Status") Integer Status);
}
