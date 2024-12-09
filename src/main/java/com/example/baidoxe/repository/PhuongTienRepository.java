package com.example.baidoxe.repository;

import com.example.baidoxe.dto.PhuongTienDTO;
import com.example.baidoxe.models.PhuongTien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhuongTienRepository extends JpaRepository<PhuongTien, Integer> {

    // Tìm tất cả phương tiện của người dùng với userId động
    @Query("SELECT pt FROM PhuongTien pt WHERE pt.users.Id = :userId")
    List<PhuongTien> findByUserId(@Param("userId") Integer userId);

    // Tìm phương tiện theo id và userId
    Optional<PhuongTien> findByIdAndUsersId(Integer id, Integer userId);

    @Query("SELECT new com.example.baidoxe.dto.PhuongTienDTO(" +
            "pt.Id, pt.LoaiPhuongTien,pt.Hang,pt.BienSo,pt.Status,pt.users.Id) " +
            "FROM PhuongTien pt " +
            "JOIN Users u ON u.Id = pt.users.Id " + // thêm khoảng trắng sau User_Id
            "WHERE pt.Status = 1 AND pt.users.Id = :userId")
    List<PhuongTienDTO> findPhuongTienUser(@Param("userId") Integer userId);

    Optional<PhuongTien> findById(Integer id);
}
