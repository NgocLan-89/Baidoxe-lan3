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
import java.util.List;
import java.util.Optional;
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
        DatCho savedDatCho = datChoRepository.save(datCho);

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
}
