package com.example.baidoxe.repository;

import com.example.baidoxe.dto.ThanhToanDTO;
import com.example.baidoxe.models.DatCho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThanhToanRepository extends JpaRepository<DatCho, Integer> {

    // Define JPQL query using @Query
    @Query("SELECT new com.example.baidoxe.dto.ThanhToanDTO(" +
            "d.Id, d.DangKyGioVao, d.DangKyGioRa, d.Status, d.viTriDo.Id, d.MaQR, " +
            "p.Id, p.LoaiPhuongTien, p.BienSo, n.TenNganHang, n.SoTaiKhoan, " +
            "v.ChiTietViTri, b.TenBaiDo, b.DiaChi, b.Id, u.Id, n.Id) " +
            "FROM DatCho d " +
            "JOIN d.phuongTien p " +
            "JOIN d.viTriDo v " +
            "JOIN v.baiDo b " +
            "JOIN p.users u " +
            "JOIN u.nganHang n " +
            "WHERE d.Status = 1")
    List<ThanhToanDTO> findThanhToan();

    @Query(value = "SELECT MONTH(d.DangKyGioVao) AS month, COUNT(*) AS count " +
            "FROM DatCho d " +
            "WHERE d.DangKyGioVao IS NOT NULL " +
            "GROUP BY MONTH(d.DangKyGioVao) " +
            "ORDER BY month",
            nativeQuery = true)
    List<Object[]> countBookingsByMonth();

    @Query(value = "SELECT YEAR(d.DangKyGioVao) AS Nam, " +
            "MONTH(d.DangKyGioVao) AS Thang, " +
            "COUNT(*) AS SoLuongDatCho " +
            "FROM DatCho d " +
            "WHERE d.DangKyGioVao IS NOT NULL " +
            "GROUP BY YEAR(d.DangKyGioVao), MONTH(d.DangKyGioVao) " +
            "ORDER BY Nam, Thang",
            nativeQuery = true)
    List<Object[]> countBookingsByYearAndMonth();



}