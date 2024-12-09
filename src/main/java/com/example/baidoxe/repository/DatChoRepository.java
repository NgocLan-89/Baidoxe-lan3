package com.example.baidoxe.repository;
import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.models.DatCho;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository

public interface DatChoRepository extends JpaRepository<DatCho, Integer> {
    Optional<DatCho> findById(Integer Id);
    @Query("SELECT dc FROM DatCho dc WHERE dc.Status = 1")
    List<DatChoDTO> findAll(@Param("Status") Integer Status);
    @Query("SELECT v.Id, v.DangKyGioRa FROM DatCho v WHERE v.viTriDo = :Id")
    List<Object[]> getIdAndDangKyGioRa(@Param("Id") Integer Id);
    /**

     * Cập nhật thời gian DangKyGioRa.

     *

     * @param datChoId Id của DatCho cần cập nhật.

     * @param newDangKyGioRa Thời gian mới cần cập nhật.

     */

    @Modifying

    @Transactional

    @Query("UPDATE DatCho d SET d.DangKyGioRa = :newDangKyGioRa WHERE d.Id = :datChoId")
    void updateDangKyGioRa(@Param("datChoId") Integer datChoId,
                           @Param("newDangKyGioRa") LocalDateTime newDangKyGioRa);
    /**

     * Tìm Id của DatCho dựa trên baiDoId và chiTietViTri.

     *

     * @param baiDoId Id của bãi đỗ.

     * @param chiTietViTri Vị trí chi tiết trong bãi đỗ.

     * @return Id của DatCho.

     */

    @Query("SELECT d.Id FROM DatCho d " +
            "JOIN ViTriDo v ON d.viTriDo.Id = v.Id " +
            "WHERE v.baiDo.Id = :baiDoId AND v.ChiTietViTri = :chiTietViTri")
    Integer findDatChoId(@Param("baiDoId") Integer baiDoId,
                         @Param("chiTietViTri") Integer chiTietViTri);

    @Query("SELECT d FROM DatCho d JOIN d.viTriDo v WHERE d.DangKyGioRa < :currentTime AND v.Status = 2")
    List<DatCho> findExpiredReservations(@Param("currentTime") LocalDateTime currentTime);

}