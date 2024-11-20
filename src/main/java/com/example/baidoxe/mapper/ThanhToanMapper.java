package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.ThanhToanDTO;
import com.example.baidoxe.models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ThanhToanMapper {

    // Ánh xạ từ DatCho thành ThanhToanDTO
    public ThanhToanDTO toDTO(DatCho datCho) {
        ThanhToanDTO ttDTO = new ThanhToanDTO();

        // Ánh xạ các trường cơ bản từ DatCho
        ttDTO.setId(datCho.getId());
        ttDTO.setDangKyGioVao(LocalDateTime.from(datCho.getDangKyGioVao()));
        ttDTO.setDangKyGioRa(LocalDateTime.from(datCho.getDangKyGioRa()));

        // Nếu có thông tin phương tiện, ánh xạ các trường liên quan
        if (datCho.getPhuongTien() != null) {
            PhuongTien phuongTien = datCho.getPhuongTien();
            ttDTO.setBienSo(phuongTien.getBienSo());
            ttDTO.setLoaiPhuongTien(phuongTien.getLoaiPhuongTien());

            // Lấy thông tin ngân hàng của người dùng
            if (phuongTien.getUsers() != null && phuongTien.getUsers().getNganHang() != null) {
                NganHang nganHang = phuongTien.getUsers().getNganHang();
                ttDTO.setTenNganHang(nganHang.getTenNganHang());
                ttDTO.setSoTaiKhoan(nganHang.getSoTaiKhoan());
                ttDTO.setNganHang_Id(nganHang.getId());  // ánh xạ NganHang_Id
            }
            ttDTO.setUser_Id(phuongTien.getUsers().getId()); // ánh xạ User_Id
        }

        // Nếu có thông tin vị trí, ánh xạ chi tiết vị trí
        if (datCho.getViTriDo() != null) {
            ViTriDo viTriDo = datCho.getViTriDo();
            ttDTO.setChiTietViTri(viTriDo.getChiTietViTri());
            ttDTO.setViTriDo_Id(viTriDo.getId()); // ánh xạ ViTriDo_Id
        }

        // Nếu có thông tin bãi đỗ, ánh xạ thông tin bãi đỗ
        if (datCho.getViTriDo() != null && datCho.getViTriDo().getBaiDo() != null) {
            BaiDo baiDo = datCho.getViTriDo().getBaiDo();
            ttDTO.setTenBaiDo(baiDo.getTenBaiDo());
            ttDTO.setDiaChi(baiDo.getDiaChi());
            ttDTO.setBaiDo_Id(baiDo.getId());  // ánh xạ BaiDo_Id
        }

        return ttDTO;
    }

    // Ánh xạ từ ThanhToanDTO thành DatCho
    public DatCho toEntity(ThanhToanDTO ttDTO) {
        DatCho datCho = new DatCho();

        // Cập nhật các trường cơ bản
        datCho.setId(ttDTO.getId());
        datCho.setDangKyGioVao(LocalDateTime.from(ttDTO.getDangKyGioVao()));
        datCho.setDangKyGioRa(LocalDateTime.from(ttDTO.getDangKyGioRa()));

        // Nếu có thông tin phương tiện, ánh xạ thông tin phương tiện
        if (ttDTO.getBienSo() != null && ttDTO.getLoaiPhuongTien() != null) {
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setBienSo(ttDTO.getBienSo());
            phuongTien.setLoaiPhuongTien(ttDTO.getLoaiPhuongTien());

            // Nếu có thông tin người dùng và ngân hàng, ánh xạ thông tin người dùng
            if (ttDTO.getTenNganHang() != null && ttDTO.getSoTaiKhoan() != null) {
                Users users = new Users();
                NganHang nganHang = new NganHang();
                nganHang.setTenNganHang(ttDTO.getTenNganHang());
                nganHang.setSoTaiKhoan(ttDTO.getSoTaiKhoan());
                users.setNganHang(nganHang);
                phuongTien.setUsers(users);
            }

            datCho.setPhuongTien(phuongTien);
        }

        // Nếu có thông tin vị trí và bãi đỗ, ánh xạ thông tin vị trí
        if (ttDTO.getChiTietViTri() != null && ttDTO.getTenBaiDo() != null) {
            ViTriDo viTriDo = new ViTriDo();
            BaiDo baiDo = new BaiDo();
            baiDo.setTenBaiDo(ttDTO.getTenBaiDo());
            baiDo.setDiaChi(ttDTO.getDiaChi());
            viTriDo.setBaiDo(baiDo);
            viTriDo.setChiTietViTri(ttDTO.getChiTietViTri());

            datCho.setViTriDo(viTriDo);
        }

        return datCho;
    }
}
