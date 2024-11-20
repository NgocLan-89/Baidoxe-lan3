package com.example.baidoxe.mapper;

import ch.qos.logback.core.model.Model;
import com.example.baidoxe.dto.PhuongTienDTO;
import com.example.baidoxe.dto.UsersDTO;
import com.example.baidoxe.models.NganHang;
import com.example.baidoxe.models.PhuongTien;
import com.example.baidoxe.models.Role;
import com.example.baidoxe.models.Users;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsersMapper {

    // Mapping from Users to UsersDTO
    public UsersDTO toUsersDTO(Users users) {
        if (users == null) {
            return null;
        }

        // Kiểm tra xem có phương tiện không
        PhuongTien firstPhuongTien = (users.getPhuongTiens() != null && !users.getPhuongTiens().isEmpty())
                ? users.getPhuongTiens().iterator().next() : null;

        return UsersDTO.builder()
                .Id(users.getId())  // Đảm bảo phương thức gọi đúng chữ thường
                .HoTen(users.getHoTen())
                .SDT(users.getSDT())
                .Email(users.getEmail())
                .Password(users.getPassword())
                .TaiKhoan(users.getTaiKhoan())
                .Status(users.getStatus())
                .Image(users.getImage())
                .Role_Id(users.getRole() != null ? users.getRole().getId() : null)
                .Role_name(users.getRole() != null ? users.getRole().getRole_name() : null)
                .NganHangId(users.getNganHang() != null ? users.getNganHang().getId() : null)
                .TenNganHang(users.getNganHang() != null ? users.getNganHang().getTenNganHang() : null)
                .SoTaiKhoan(users.getNganHang() != null ? users.getNganHang().getSoTaiKhoan() : null)
                .build();
    }

    // Mapping from UsersDTO to Users
    public Users toUsers(UsersDTO usersDTO) {
        if (usersDTO == null) {
            return null;
        }

        Users users = new Users();
        users.setId(usersDTO.getId());
        users.setHoTen(usersDTO.getHoTen());
        users.setSDT(usersDTO.getSDT());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setTaiKhoan(usersDTO.getTaiKhoan());
        users.setImage(usersDTO.getImage());
        if(usersDTO.getId()==null){
            users.setStatus(1);
        }else {
            users.setStatus(users.getStatus());
        }
        // Mapping Role
        if (usersDTO.getRole_Id() != null) {
            Role role = new Role();
            role.setId(usersDTO.getRole_Id());  // Corrected to use RoleId from DTO
            role.setRole_name(usersDTO.getRole_name());
            users.setRole(role);
        }

        // Mapping NganHang
        if (usersDTO.getNganHangId() != null) {
            NganHang nganHang = new NganHang();
            nganHang.setId(usersDTO.getNganHangId());  // Corrected to use NganHangId from DTO
            nganHang.setTenNganHang(usersDTO.getTenNganHang());
            nganHang.setSoTaiKhoan(usersDTO.getSoTaiKhoan());
            users.setNganHang(nganHang);
        }

        // Mapping list of PhuongTien

        return users;
    }


}