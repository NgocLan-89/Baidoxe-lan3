package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.mapper.BaiDoMapper;
import com.example.baidoxe.mapper.NganHangMapper;
import com.example.baidoxe.models.BaiDo;
import com.example.baidoxe.models.NganHang;
import com.example.baidoxe.repository.BaiDoRepository;
import com.example.baidoxe.repository.NganHangRepository;
import com.example.baidoxe.service.NganHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NganHangServiceImpl implements NganHangService {
    @Autowired
    private NganHangRepository nganHangRepository;

    @Autowired
    private NganHangMapper nganHangMapper;


    @Override
    public List<NganHangDTO> listActiveNganHang() {
        List<NganHang> activeNganHang= nganHangRepository.findAll()
                .stream()
                .filter(nganHang -> nganHang.getStatus()==1)
                .collect(Collectors.toList());
        return activeNganHang.stream()
                .map(nganHangMapper::tonganHangDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NganHangDTO findNganHangById(Integer Id) {
        NganHang nganHang = nganHangRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bãi đỗ với ID: " + Id));
        return nganHangMapper.tonganHangDTO(nganHang);
    }

    @Override
    public NganHangDTO addNganHang(NganHangDTO nganHangDTO) {
        NganHang nganHang = new NganHang();
        nganHang.setTenNganHang(nganHangDTO.getTenNganHang());
        nganHang.setSoTaiKhoan(nganHangDTO.getSoTaiKhoan());
        nganHang.setStatus(1);
        NganHang savedNganHang = nganHangRepository.save(nganHang);

// Chuyển đối tượng đã lưu thành DTO và trả về
        NganHangDTO savedNganHangDTO = new NganHangDTO();
        savedNganHangDTO.setId(savedNganHang.getId());
        savedNganHangDTO.setTenNganHang(savedNganHang.getTenNganHang());
        savedNganHangDTO.setSoTaiKhoan(savedNganHang.getSoTaiKhoan());
        savedNganHangDTO.setStatus(savedNganHang.getStatus());

        return savedNganHangDTO;
    }

    @Override
    public NganHangDTO updateNganHang(NganHangDTO nganHangDTO) {
        NganHang suaNganHang = nganHangRepository.findById(nganHangDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bãi đỗ với ID: " + nganHangDTO.getId()));

        suaNganHang.setTenNganHang(nganHangDTO.getTenNganHang());
        suaNganHang.setSoTaiKhoan(nganHangDTO.getSoTaiKhoan());
        suaNganHang.setStatus(1);

        NganHang updateNganHang = nganHangRepository.save(suaNganHang);

        return nganHangMapper.tonganHangDTO(updateNganHang);
    }

    @Override
    public void deleteNganHang(Integer Id) {
        // Kiểm tra ID có hợp lệ không
        if (Id == null || Id <= 0) {
            throw new IllegalArgumentException("ID không hợp lệ: " + Id);
        }

        // Tìm bãi đỗ theo ID
        NganHang nganHang = nganHangRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bãi đỗ với ID: " + Id));

        // Cập nhật trạng thái thành 0
        nganHang.setStatus(0);
        nganHangRepository.save(nganHang);
    }
}