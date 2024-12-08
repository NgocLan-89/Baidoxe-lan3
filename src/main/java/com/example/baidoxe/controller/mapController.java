package com.example.baidoxe.controller;

import com.example.baidoxe.Provide.QRCodeGenerator;
import com.example.baidoxe.dto.*;
import com.example.baidoxe.models.BaiDo;
import com.example.baidoxe.models.DatCho;
import com.example.baidoxe.models.PhuongTien;
import com.example.baidoxe.models.ViTriDo;
import com.example.baidoxe.repository.PhuongTienRepository;
import com.example.baidoxe.service.*;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/map")
public class mapController {

    private static final Logger logger = LoggerFactory.getLogger(mapController.class);

    @Autowired
    private BaiDoService baiDoService;
    @Autowired
    private ViTriDoService viTriDoService;
    @Autowired
    private PhuongTienService phuongTienService;
    @Autowired
    private DatChoService datChoService;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    @Autowired
    private ThanhToanService thanhToanService;
    @Autowired
    private ThongTinDoService thongTinDoService;


    @GetMapping("/list")
    public String listBaiDo(Model model, HttpSession session) {
        // Lấy giá trị từ session
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");

        // Kiểm tra xem session có hợp lệ không
        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
        }

        // Fetch the list of BaiDo from the repository
        List<BaiDoDTO> baiDoListDTO = baiDoService.findActiveBaiDo();
// Tạo danh sách chứa số lượng vị trí còn trống
        Map<Integer, Integer> soLuongConTrongMap = new HashMap<>();

        for (BaiDoDTO baiDo : baiDoListDTO) {
            int soLuongConTrong = viTriDoService.countActiveViTriDoByBaiDoId(baiDo.getId());
            soLuongConTrongMap.put(baiDo.getId(), soLuongConTrong);
        }
        model.addAttribute("soLuongConTrongMap", soLuongConTrongMap);

        // Add the list and session info to the model
        model.addAttribute("listBaiDo", baiDoListDTO);
        model.addAttribute("idUser", idUser);
        model.addAttribute("roleId", roleId);

        return "map/map";  // No need for .html, Thymeleaf resolves it automatically
    }
    /*    @GetMapping("/datChoView")
        public String datChoView(Model model) {
            // Fetch the list of BaiDo from the repository
            List<BaiDoDTO> baiDoListDTO = baiDoService.listActiveBaiDo();
            // Add the list to the model with the key 'listBaiDo'
            model.addAttribute("listBaiDo", baiDoListDTO);
            return "map/khbaidoABCD";  // No need for .html, Thymeleaf resolves it automatically
        }*/
    @GetMapping("/datcho/{Id}")
    public String datChoAction(@PathVariable("Id") Integer Id, Model model, HttpSession session) {
        // Lấy giá trị từ session
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");

        // Kiểm tra xem session có hợp lệ không
        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
        }
        BaiDoDTO baiDoDTO = baiDoService.findBaiDoById(Id);
        model.addAttribute("baiDo", baiDoDTO);

        int soLuongConTrong = viTriDoService.countActiveViTriDoByBaiDoId(Id);
        model.addAttribute("soLuongConTrong", soLuongConTrong);

        List<ViTriDoDTO> viTriDoDTOS = viTriDoService.getActiveViTriDoByBaiDoId(Id);
        model.addAttribute("ListViTriDo", viTriDoDTOS);

        List<PhuongTienDTO> phuongTienDTO=phuongTienService.findPhuongTienUser(idUser);
        model.addAttribute("bienSo", phuongTienDTO);
        // Thêm idUser vào model để hiển thị thông tin người dùng nếu cần
        model.addAttribute("idUser", idUser);

        return "map/khbaidoABCD";
    }

    @PostMapping("/datcho/{Id}")
    public ModelAndView datCho(@PathVariable("Id") Integer id,
                               @RequestParam("selectedVehicleId") Integer vehicleId,
                               @RequestParam("IdViTriDo") Integer viTriDoId,
                               @RequestParam("DangKyGioVao") String DangKyGioVao,
                               @RequestParam("DangKyGioRa") String DangKyGioRa,
                               @RequestParam("bienSo") String bienSo,
                               Model model,
                               HttpSession session) {

        // Lấy giá trị từ session
        Integer idUser = (Integer) session.getAttribute("IdUser");

        if (idUser == null) {
            return new ModelAndView("redirect:/login/auth"); // Chuyển hướng nếu session không hợp lệ
        }

        // Định dạng phù hợp với datetime-local từ HTML
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Chuyển đổi chuỗi thành LocalDateTime
        LocalDateTime gioVao = LocalDateTime.parse(DangKyGioVao, formatter);
        LocalDateTime gioRa = LocalDateTime.parse(DangKyGioRa, formatter);


        // Tạo đối tượng DatChoDTO
        DatChoDTO datChoDTO = new DatChoDTO();
        datChoDTO.setPhuongTien_Id(vehicleId);
        datChoDTO.setViTriDo_Id(viTriDoId);
        datChoDTO.setDangKyGioVao(gioVao);
        datChoDTO.setDangKyGioRa(gioRa);

        // Cập nhật trạng thái vị trí đỗ
        viTriDoService.ApdateStatus(viTriDoId);

        // Tạo mã QR từ biển số xe
        String qrCode = "QR_" + bienSo; // Dùng biển số xe để tạo mã QR
        String qrCodeUrl = qrCodeGenerator.generateQRCodeUrl(qrCode);  // Tạo URL cho mã QR

        // Thêm mã QR vào đối tượng DatChoDTO và model
        datChoDTO.setMaQR(qrCode);  // Lưu mã QR vào đối tượng DatChoDTO
        model.addAttribute("qrCodeUrl", qrCodeUrl);  // Thêm URL mã QR vào model

        // Lưu thông tin DatCho vào cơ sở dữ liệu
        DatChoDTO datCho = datChoService.addDatCho(datChoDTO);

        // Điều hướng đến trang thanh toán với ID của DatCho
        return new ModelAndView("redirect:/thanhtoan/tratien/" + datCho.getId());
    }

}
