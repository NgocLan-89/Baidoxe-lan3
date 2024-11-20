package com.example.baidoxe.repository;

import com.example.baidoxe.dto.ThongTinDoDTO;
import com.example.baidoxe.models.ThongTinDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThongTinDoRepository extends JpaRepository<ThongTinDo, Integer> {

    @Query("SELECT new com.example.baidoxe.dto.ThongTinDoDTO(" +
            "t.Id, t.ThoiGianVao, t.ThoiGianRa, t.datCho.Id, t.Status, " +
            "u.HoTen, u.Email, b.TenBaiDo, v.ChiTietViTri, n.TenNganHang, n.SoTaiKhoan, p.BienSo, " +
            "d.Id, b.Id, p.Id, u.Id, n.Id) " +
            "FROM ThongTinDo t " +
            "JOIN t.datCho d " +
            "JOIN d.phuongTien p " +
            "JOIN p.users u " +
            "JOIN u.nganHang n " +
            "JOIN d.viTriDo v " +
            "JOIN v.baiDo b " +
            "WHERE t.Status = :status AND u.Id = :userId")
    List<ThongTinDoDTO> findThongTinDoByUserAndStatus(@Param("userId") int userId, @Param("status") int status);

}
