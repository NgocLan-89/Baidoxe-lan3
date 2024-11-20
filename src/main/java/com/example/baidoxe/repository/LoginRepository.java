package com.example.baidoxe.repository;

import com.example.baidoxe.dto.LoginDTO;
import com.example.baidoxe.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Users,Integer> {
    @Query("SELECT new com.example.baidoxe.dto.LoginDTO(u.Id, r.Id) " +
            "FROM Users u JOIN u.role r " +
            "WHERE u.TaiKhoan = :TaiKhoan AND u.Password = :Password AND r.Id = :roleId AND u.Status = 1")
    Optional<LoginDTO> findUserLogin(@Param("TaiKhoan") String taiKhoan,
                                     @Param("Password") String password,
                                     @Param("roleId") Integer roleId);
}