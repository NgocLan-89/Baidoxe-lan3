package com.example.baidoxe.controller;

import com.example.baidoxe.Provide.QRCodeGenerator;
import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.dto.ThanhToanDTO;
import com.example.baidoxe.dto.ThongTinDoDTO;
import com.example.baidoxe.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/thanhtoan")
public class ThanhToanController {

    @Autowired
    private ThanhToanService thanhToanService;

    @Autowired
    private DatChoService datChoService;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    @Autowired
    private ThongTinDoService thongTinDoService;  // Đảm bảo inject thongTinDoService



    @GetMapping("/tratien/{id}")
    public String showThanhToan(@PathVariable("id") int Id, Model model) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Lấy đối tượng DatCho từ cơ sở dữ liệu theo ID
        DatChoDTO datChoDTO = datChoService.finDatChoById(Id);

        // Nếu tìm thấy, lấy mã QR và thêm vào model
        if (datChoDTO != null) {
            String qrCodeUrl = qrCodeGenerator.generateQRCodeUrl(datChoDTO.getMaQR());
            model.addAttribute("qrCodeUrl", qrCodeUrl);
        }
        ThanhToanDTO thanhToanDTO = thanhToanService.finThanhToanById(Id);

        model.addAttribute("thanhtoandt", thanhToanDTO);
        model.addAttribute("datChoDTO", datChoDTO);


        return "map/thanhtoan";
    }

    @PostMapping("/tratien/{id}")
    public String addThongTinDo(@PathVariable("id") Integer id,
                                @RequestParam("DangKyGioVao") String ThoiGianVao,
                                @RequestParam("DangKyGioRa") String ThoiGianRa,
                                @RequestParam("DatCho_Id") Integer baiDoId,
                                Model model) {

        // Định dạng cho chuỗi thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // Chuyển đổi thời gian từ String sang LocalDateTime
        LocalDateTime gioVao = LocalDateTime.parse(ThoiGianVao, formatter);
        LocalDateTime gioRa = LocalDateTime.parse(ThoiGianRa, formatter);

        // Tìm thông tin DatCho

        DatChoDTO datChoDTO = datChoService.finDatChoById(id);
        // Nếu tìm thấy, lấy mã QR và thêm vào model
        if (datChoDTO != null) {
            String qrCodeUrl = qrCodeGenerator.generateQRCodeUrl(datChoDTO.getMaQR());
            model.addAttribute("qrCodeUrl", qrCodeUrl);
        }

        // Tạo đối tượng ThongTinDoDTO với các thông tin cần thiết
        ThongTinDoDTO thongTinDoDTO = new ThongTinDoDTO();
        thongTinDoDTO.setThoiGianVao(gioVao);     // Set giờ vào
        thongTinDoDTO.setThoiGianRa(gioRa);       // Set giờ ra
        thongTinDoDTO.setDatCho_Id(baiDoId);      // Truyền ID bãi đỗ thay vì DatChoDTO
        thongTinDoDTO.setStatus(1);

        // Lưu thông tin vào DB
        thongTinDoService.addThongTinDo(thongTinDoDTO);
        return "map/QRthanhToan";
    }
}
