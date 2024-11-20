package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.PhuongTienDTO;
import com.example.baidoxe.mapper.PhuongTienMapper;
import com.example.baidoxe.models.PhuongTien;
import com.example.baidoxe.repository.PhuongTienRepository;
import com.example.baidoxe.service.PhuongTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhuongTienServiceImpl implements PhuongTienService {

    @Autowired
    private PhuongTienRepository phuongTienRepository;

    @Autowired
    private PhuongTienMapper phuongTienMapper;
    @Override
    public List<PhuongTienDTO> findPhuongTienUser(Integer userId) {
        return phuongTienRepository.findPhuongTienUser(userId);
    }

    @Override
    public List<PhuongTienDTO> findByUserId(Integer userId) {
        List<PhuongTien> phuongTienList = phuongTienRepository.findByUserId(userId);
        return phuongTienList.stream()
                .map(phuongTienMapper::toPhuongTienDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PhuongTienDTO findById(Integer id, Integer userId) {
        PhuongTien phuongTien = phuongTienRepository.findByIdAndUsersId(id, userId)
                .orElseThrow(() -> new RuntimeException("Phương tiện không tìm thấy"));
        return phuongTienMapper.toPhuongTienDTO(phuongTien);
    }

    @Override
    public PhuongTienDTO addPhuongTien(PhuongTienDTO phuongTienDTO) {
        PhuongTien phuongTien = phuongTienMapper.toPhuongTien(phuongTienDTO);
        phuongTien.setStatus(1);
        phuongTien = phuongTienRepository.save(phuongTien);
        return phuongTienMapper.toPhuongTienDTO(phuongTien);
    }

    @Override
    public PhuongTienDTO updatePhuongTien(PhuongTienDTO phuongTienDTO) {
        PhuongTien phuongTien = phuongTienRepository.findByIdAndUsersId(phuongTienDTO.getId(), phuongTienDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Phương tiện không tìm thấy hoặc bạn không có quyền sửa"));
        phuongTien.setLoaiPhuongTien(phuongTienDTO.getLoaiPhuongTien());
        phuongTien.setHang(phuongTienDTO.getHang());
        phuongTien.setBienSo(phuongTienDTO.getBienSo());
        phuongTien.setStatus(phuongTienDTO.getStatus());
        phuongTien = phuongTienRepository.save(phuongTien);
        return phuongTienMapper.toPhuongTienDTO(phuongTien);
    }

    @Override
    public void deletePhuongTien(Integer id, Integer userId) {
        PhuongTien phuongTien = phuongTienRepository.findByIdAndUsersId(id, userId)
                .orElseThrow(() -> new RuntimeException("Phương tiện không tìm thấy hoặc bạn không có quyền xóa"));
        phuongTien.setStatus(0); // Cập nhật trạng thái thành 0 (không sử dụng)
        phuongTienRepository.save(phuongTien); // Lưu thay đổi vào cơ sở dữ liệu
    }
}
