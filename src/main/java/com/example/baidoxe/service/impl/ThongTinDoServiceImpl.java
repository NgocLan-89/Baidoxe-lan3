package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.ThongTinDoDTO;
import com.example.baidoxe.mapper.ThongTinDoMapper;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.models.ThongTinDo;
import com.example.baidoxe.repository.DatChoRepository;
import com.example.baidoxe.repository.ThongTinDoRepository;
import com.example.baidoxe.service.ThongTinDoService;
import jakarta.persistence.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThongTinDoServiceImpl implements ThongTinDoService {

    @Autowired
    private ThongTinDoRepository thongTinDoRepository;

    @Autowired
    private ThongTinDoMapper thongTinDoMapper;
    @Autowired
    private DatChoRepository datChoRepository;
    @Override
    public List<ThongTinDoDTO> getAllThongTinDo() {
        // Phương thức này lấy tất cả thông tin
        List<ThongTinDo> thongTinDos = thongTinDoRepository.findAll();
        return thongTinDos.stream()
                .map(thongTinDoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ThongTinDoDTO> getThongTinDoByUserAndStatus(int userId, int status) {
        List<ThongTinDoDTO> thongTinDos = thongTinDoRepository.findThongTinDoByUserAndStatus(userId, status);
        return thongTinDos;
    }


    @Override
    public void addThongTinDo(ThongTinDoDTO thongTinDoDTO) {
        ThongTinDo thongTinDo = new ThongTinDo();

        // Gán giá trị từ DTO sang entity
        thongTinDo.setThoiGianVao(thongTinDoDTO.getThoiGianVao());
        thongTinDo.setThoiGianRa(thongTinDoDTO.getThoiGianRa());
        thongTinDo.setStatus(thongTinDoDTO.getStatus());

        // Lấy đối tượng DatCho từ cơ sở dữ liệu nếu có
        if (thongTinDoDTO.getDatCho_Id() != null) {
            DatCho datCho = datChoRepository.findById(thongTinDoDTO.getDatCho_Id())
                    .orElseThrow(() -> new RuntimeException("DatCho không tồn tại với Id: " + thongTinDoDTO.getDatCho_Id()));
            thongTinDo.setDatCho(datCho);
        }

        // Lưu đối tượng ThongTinDo vào cơ sở dữ liệu
        thongTinDoRepository.save(thongTinDo);
    }
}
