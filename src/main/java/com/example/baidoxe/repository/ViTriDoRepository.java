package com.example.baidoxe.repository;

import com.example.baidoxe.dto.ViTriDoDTO;
import com.example.baidoxe.models.ViTriDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViTriDoRepository extends JpaRepository<ViTriDo, Integer> {

    Optional<ViTriDo> findById(Integer Id);

    @Query("SELECT COUNT(vd) FROM ViTriDo vd WHERE vd.Status = 1")
    int countByStatus();

    @Query("SELECT new com.example.baidoxe.dto.ViTriDoDTO(" +
            "u.Id, u.ChiTietViTri, u.Status, " + // Chắc chắn rằng tên các thuộc tính là đúng và tương ứng với ViTriDoDTO
            "b.Id, b.TenBaiDo) " + // Không có dấu phẩy ở đây
            "FROM ViTriDo u " +
            "LEFT JOIN u.baiDo b " + // Kiểm tra xem `baiDo` có phải là thuộc tính trong ViTriDo hay không
            "WHERE u.Status = :status")
    List<ViTriDoDTO> findActiveViTriDo(@Param("status") Integer status);

    @Query("SELECT v FROM ViTriDo v WHERE v.Status IN (1, 2)")
    List<ViTriDo> findViTriDoByStatus1Or2();
    // Count active ViTriDo by baiDoId
    @Query("SELECT COUNT(bd) FROM ViTriDo bd WHERE bd.Status = 1 AND bd.baiDo.Id = :baiDoId")
    int countByStatusAndBaiDoId(@Param("baiDoId") Integer baiDoId);
    @Query("SELECT bd FROM ViTriDo bd WHERE bd.Status = 1 AND bd.baiDo.Id = :baiDoId")
    List<ViTriDo> findActiveViTriDoByBaiDoId(@Param("baiDoId") Integer baiDoId);
}
