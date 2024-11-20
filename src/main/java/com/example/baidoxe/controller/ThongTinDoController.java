package com.example.baidoxe.controller;

import com.example.baidoxe.Provide.QRCodeGenerator;
import com.example.baidoxe.dto.DatChoDTO;
import com.example.baidoxe.dto.ThongTinDoDTO;
import com.example.baidoxe.service.DatChoService;
import com.example.baidoxe.service.ThongTinDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/hoadon")
public class ThongTinDoController {

    @Autowired
    private ThongTinDoService thongTinDoService;
    @Autowired
    private DatChoService datChoService;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    private static final Logger logger = LoggerFactory.getLogger(ThongTinDoController.class);

    @GetMapping("/chitiet/{id}")
    public String showHoaDon(@PathVariable("id") int Id, Model model, HttpSession session) {
        // Lấy thông tin từ session
        Integer IdUser = (Integer) session.getAttribute("IdUser");
        Integer RoleId = (Integer) session.getAttribute("RoleId");

        // Kiểm tra xem thông tin session có hợp lệ không
        if (IdUser == null || RoleId == null) {
            // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
            return "redirect:/login/auth";
        }

        // Nếu session hợp lệ, tiếp tục lấy thông tin hóa đơn
        int Status = 1; // Giả sử bạn đang chỉ định trạng thái là 1 cho tất cả
        List<ThongTinDoDTO> thongTinDoDTOS = thongTinDoService.getThongTinDoByUserAndStatus(IdUser, Status);

        // Log thông tin về thời gian vào và ra
        thongTinDoDTOS.forEach(thongTinDo -> {
            logger.info("thoiGianVao: " + thongTinDo.getThoiGianVao());
            logger.info("thoiGianRa: " + thongTinDo.getThoiGianRa());
        });
        DatChoDTO datChoDTO = datChoService.finDatChoById(Id);
        // Nếu tìm thấy, lấy mã QR và thêm vào model
        if (datChoDTO != null) {
            String qrCodeUrl = qrCodeGenerator.generateQRCodeUrl(datChoDTO.getMaQR());
            model.addAttribute("qrCodeUrl", qrCodeUrl);
        }


        // Thêm thông tin hóa đơn vào mô hình để hiển thị
        model.addAttribute("thongTinDo", thongTinDoDTOS);
        return "map/hoadon";
    }
}
