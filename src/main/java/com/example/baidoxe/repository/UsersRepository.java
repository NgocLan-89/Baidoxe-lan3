package com.example.baidoxe.repository;

import com.example.baidoxe.dto.UsersDTO;
import com.example.baidoxe.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(Integer Id);

    @Query("SELECT new com.example.baidoxe.dto.UsersDTO(" +
            "u.Id, u.HoTen, u.SDT, u.Email, u.TaiKhoan, u.nganHang.SoTaiKhoan, u.Status, u.Password, u.Image, " +
            "ng.Id, ng.TenNganHang, " +
            "r.Id, r.Role_name) " +
            "FROM Users u " +
            "LEFT JOIN u.nganHang ng " +
            "LEFT JOIN u.role r " +
            "WHERE u.Status = :Status")
    List<UsersDTO> findActiveUsers(@Param("Status") Integer Status);


}