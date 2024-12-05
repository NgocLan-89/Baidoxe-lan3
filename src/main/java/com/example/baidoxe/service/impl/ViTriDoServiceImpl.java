package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.ViTriDoDTO;
import com.example.baidoxe.mapper.ViTriDoMapper;
import com.example.baidoxe.models.BaiDo;
import com.example.baidoxe.models.ViTriDo;
import com.example.baidoxe.repository.BaiDoRepository;
import com.example.baidoxe.repository.ViTriDoRepository;
import com.example.baidoxe.service.ViTriDoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ViTriDoServiceImpl implements ViTriDoService {

    @Autowired
    private ViTriDoRepository viTriDoRepository;
    @Autowired
    private BaiDoRepository baiDoRepository;

    @Autowired
    private ViTriDoMapper viTriDoMapper;

    @Override
    public List<ViTriDoDTO> listActiveViTriDo() {
        List<ViTriDo> activeViTriDos = viTriDoRepository.findViTriDoByStatus1Or2();
        return activeViTriDos.stream()
                .map(viTriDoMapper::toViTriDoDTO)
                .collect(Collectors.toList());

    }
    private static final Logger logger = LoggerFactory.getLogger(ViTriDoServiceImpl.class);


    @Override
    public int countByStatus() {
        return viTriDoRepository.countByStatus();
    }

    @Override
    public ViTriDoDTO addViTriDo(ViTriDoDTO viTriDoDTO) {
        // Lấy bãi đỗ theo ID
        BaiDo baiDo = baiDoRepository.findById(viTriDoDTO.getBaiDo_Id())
                .orElseThrow(() -> new RuntimeException("Bãi đỗ không tồn tại")); // Ném ngoại lệ nếu không tìm thấy

        // Chuyển đổi ViTriDoDTO sang ViTriDo
        ViTriDo viTriDo = viTriDoMapper.toViTriDo(viTriDoDTO, baiDo);

        // Lưu vào cơ sở dữ liệu
        viTriDo = viTriDoRepository.save(viTriDo);

        // Trả về đối tượng DTO đã lưu
        return viTriDoMapper.toViTriDoDTO(viTriDo);

    }

    @Override
    public ViTriDoDTO updateViTriDo(Integer id, ViTriDoDTO viTriDoDTO) {
        Optional<ViTriDo> optionalViTriDo = viTriDoRepository.findById(id);

        if (optionalViTriDo.isPresent()) {
            ViTriDo viTriDo = optionalViTriDo.get();
            // Cập nhật các trường
            viTriDo.setChiTietViTri(viTriDoDTO.getChiTietViTri());
            viTriDo.setStatus(viTriDoDTO.getStatus());

            // Lấy bãi đỗ theo ID để cập nhật tên bãi đỗ
            BaiDo baiDo = baiDoRepository.findById(viTriDoDTO.getBaiDo_Id())
                    .orElseThrow(() -> new RuntimeException("Bãi đỗ không tồn tại")); // Ném ngoại lệ nếu không tìm thấy

            // Chỉ cập nhật tên bãi đỗ
            viTriDo.setBaiDo(baiDo); // Nếu cần thiết, nếu không thì không cần thiết lập lại bãi đỗ.

            // Lưu lại đối tượng đã cập nhật
            viTriDo = viTriDoRepository.save(viTriDo);
            return viTriDoMapper.toViTriDoDTO(viTriDo);
        } else {
            return null; // Hoặc ném ngoại lệ nếu không tìm thấy
        }

    }

    @Override
    public ViTriDoDTO deleteViTriDo(Integer id) {
        Optional<ViTriDo> optionalViTriDo = viTriDoRepository.findById(id);
        if (optionalViTriDo.isPresent()) {
            ViTriDo viTriDo = optionalViTriDo.get();
            viTriDo.setStatus(0); // Đặt status = 0 thay vì xóa
            viTriDo = viTriDoRepository.save(viTriDo); // Cập nhật lại trong cơ sở dữ liệu
            return viTriDoMapper.toViTriDoDTO(viTriDo); // Trả về DTO đã cập nhật
        } else {
            throw new IllegalArgumentException("Không tìm thấy vị trí đỗ với ID: " + id);
        }
    }

    @Override
    public ViTriDoDTO findViTriDoById(Integer Id) {
        ViTriDo viTriDo = viTriDoRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vị trí đỗ với ID: " + Id));
        return viTriDoMapper.toViTriDoDTO(viTriDo); // Trả về đối tượng DTO
    }
    @Override
    public List<ViTriDoDTO> getActiveViTriDoByBaiDoId(Integer baiDoId) {
        List<ViTriDo> viTriDoList = viTriDoRepository.findActiveViTriDoByBaiDoId(baiDoId);
        return viTriDoList.stream()
                .map(viTriDoMapper::toViTriDoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ViTriDoDTO ApdateStatus(Integer Id) {
        Optional<ViTriDo> viTriDoOptional= viTriDoRepository.findById(Id);
        if (viTriDoOptional.isPresent()){
            ViTriDo existingViTriDo = viTriDoOptional.get();
            //Cập nhật trạng thái vị trí đỗ làddaxđó Status(2)
            existingViTriDo.setStatus(3);
            viTriDoRepository.save(existingViTriDo);
        }else {
            throw new IllegalArgumentException("ViTriDo not found with ID: " + Id);
        }
        return null;
    }

    @Override
    public int countActiveViTriDoByBaiDoId(Integer baiDoId) {
        return viTriDoRepository.countByStatusAndBaiDoId(baiDoId);
    }


}
