package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.ThanhToanDTO;
import com.example.baidoxe.mapper.ThanhToanMapper;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.models.ThanhToan;
import com.example.baidoxe.repository.ThanhToanRepository;
import com.example.baidoxe.service.ThanhToanService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThanhToanServiceImpl implements ThanhToanService {

    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private ThanhToanMapper thanhToanMapper;

    // Phương thức lấy thông tin đỗ xe theo user và trạng thái

    @Override
    public List<ThanhToanDTO> getThanhToanbyPTID() {
        // Lấy danh sách từ repository với các tham số status và phuongTienId
        List<ThanhToanDTO> thanhToanDTOList = thanhToanRepository.findThanhToan();

        // Nếu không có dữ liệu, trả về danh sách trống
        if (thanhToanDTOList.isEmpty()) {
            return List.of();
        }

        return thanhToanDTOList;
    }

    @Override
    public ThanhToanDTO finThanhToanById(Integer Id) {
        Optional<DatCho> thanhToanOptional = thanhToanRepository.findById(Id);
        return thanhToanOptional.map(thanhToanMapper::toDTO).orElse(null);
    }

    // Lấy danh sách các đối tượng ThanhToanDTO từ cơ sở dữ liệu
    @Override
    public List<ThanhToanDTO> findThanhToan() {
        List<ThanhToanDTO> thanhToanDTOList = thanhToanRepository.findThanhToan();
        // Nếu không có dữ liệu, trả về danh sách trống
        if (thanhToanDTOList.isEmpty()) {
            return List.of();
        }

        return thanhToanDTOList;
    }

    @Override
    public List<Object[]> countBookingsByYearAndMonth() {
        return thanhToanRepository.countBookingsByYearAndMonth();  // Truy vấn native SQL
    }

    @Override
    public List<Object[]> countBookingsByMonth() {
        return thanhToanRepository.countBookingsByMonth();  // Truy vấn native SQL
    }
}
