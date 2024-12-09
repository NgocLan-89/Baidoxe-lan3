package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.mapper.DatChoMapper;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.models.PhuongTien;
import com.example.baidoxe.models.ViTriDo;
import com.example.baidoxe.repository.DatChoRepository;
import com.example.baidoxe.repository.PhuongTienRepository;
import com.example.baidoxe.repository.ViTriDoRepository;
import com.example.baidoxe.service.DatChoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DatChoServiceImpl implements DatChoService {
    @Autowired
    DatChoRepository datChoRepository;

    @Autowired
    DatChoMapper datChoMapper;

    @Autowired
    PhuongTienRepository phuongTienRepository;

    @Autowired
    ViTriDoRepository viTriDoRepository;

    @Override
    public DatChoDTO finDatChoById(Integer Id) {
        Optional<DatCho> datChoOptional = datChoRepository.findById(Id);
        return datChoOptional.map(datChoMapper::toDatChoDTO).orElse(null);

    }

    @Override
    public DatChoDTO addDatCho(DatChoDTO datChoDTO) {
        // Lấy PhuongTien và ViTriDo từ ID
        PhuongTien phuongTien = phuongTienRepository.findById(datChoDTO.getPhuongTien_Id()).orElse(null);
        ViTriDo viTriDo = viTriDoRepository.findById(datChoDTO.getViTriDo_Id()).orElse(null);

        // Sử dụng mapper để chuyển đổi từ DatChoDTO sang DatCho
        DatCho datCho = datChoMapper.addDatCho(datChoDTO, phuongTien, viTriDo);

        // Lưu đối tượng DatCho mới vào cơ sở dữ liệu
        DatCho savedDatCho = (DatCho) datChoRepository.save(datCho);

        // Chuyển đổi đối tượng DatCho đã lưu thành DatChoDTO để trả về
        return datChoMapper.toDatChoDTO(savedDatCho);
    }

    @Override
    public List<DatChoDTO> datChoList() {
        List<DatCho> datChoList = datChoRepository.findAll();
        return datChoList.stream()
                .map(datChoMapper::toDatChoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewReservation(DatChoDTO datChoDTO) {
        try {
            // Chuyển đổi từ DatChoDTO sang DatCho
            PhuongTien phuongTien = new PhuongTien();
            phuongTien.setId(datChoDTO.getPhuongTien_Id());

            ViTriDo viTriDo = new ViTriDo();
            viTriDo.setId(datChoDTO.getViTriDo_Id());

            DatCho datCho = datChoMapper.addDatCho(datChoDTO, phuongTien, viTriDo);

            // Lưu vào cơ sở dữ liệu
            datChoRepository.save(datCho);

            System.out.println("Đặt chỗ mới đã được thêm thành công: " + datCho);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi thêm đặt chỗ mới: " + e.getMessage());
        }
    }

    @Override
    public void extendExpiredReservations() {
        LocalDateTime currentTime = LocalDateTime.now();

        // Lấy danh sách đặt chỗ đã hết hạn và có vị trí đỗ Status = 2
        List<DatCho> expiredReservations = datChoRepository.findExpiredReservations(currentTime);

        for (DatCho oldReservation : expiredReservations) {
            // Lấy biển số xe từ PhuongTien_Id
            PhuongTien phuongTien = phuongTienRepository.findById(oldReservation.getPhuongTien().getId()).orElse(null);
            String bienSoXe = (phuongTien != null) ? phuongTien.getBienSo() : "Unknown";

            // Tạo đặt chỗ mới
            DatCho newReservation = new DatCho();
            newReservation.setDangKyGioVao(oldReservation.getDangKyGioRa());
            newReservation.setDangKyGioRa(oldReservation.getDangKyGioRa().plusHours(5));
            newReservation.setViTriDo(oldReservation.getViTriDo());
            newReservation.setPhuongTien(oldReservation.getPhuongTien());

            // Tạo mã QR với thông tin Thời gian vào, Thời gian ra và Biển số xe
            String maQR = "QR_" + oldReservation.getDangKyGioVao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm")) +
                    "_5h_"+ bienSoXe;
            newReservation.setMaQR(maQR);

            newReservation.setStatus(1); // Trạng thái mặc định là đang sử dụng

            // Lưu đặt chỗ mới vào database
            datChoRepository.save(newReservation);

            System.out.println("Đã tạo đặt chỗ mới từ đặt chỗ hết hạn: " + newReservation);
        }
    }


}
