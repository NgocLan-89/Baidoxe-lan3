package com.example.baidoxe.controller;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.service.BaiDoService;
import com.example.baidoxe.service.NganHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/nganhang")
public class NganHangController {
    @Autowired
    private NganHangService nganHangService;

    @GetMapping("/list")
    public String listNganHang(Model model) {
        List<NganHangDTO> NganHangListDTO = nganHangService.listActiveNganHang();
        model.addAttribute("listNganHang", NganHangListDTO);
        return "nganhang-list";
    }

    //Thêm dữ liệu
    @GetMapping("/them")
    public String showAddNganHangForm(Model model) {
        // Tạo một đối tượng BaiDoDTO trống và đưa vào model để form có thể sử dụng
        NganHangDTO nganHangDTO = new NganHangDTO();
        model.addAttribute("nganHangDTO", nganHangDTO);
        return "nganhang-them";
    }

    @PostMapping("/them")
    public String addNganHang(@ModelAttribute NganHangDTO nganHangDTO) {
        // Gọi service để thêm bãi đỗ
        nganHangService.addNganHang(nganHangDTO);
        return "redirect:/nganhang/list";
    }
//    Sửa dữ liệu

    @GetMapping("/sua/{id}")
    public String suaNganHang(@PathVariable Integer id, Model model) {
        NganHangDTO nganHangDTO = nganHangService.findNganHangById(id);
        model.addAttribute("nganHangDTO", nganHangDTO);
        return "nganhang-sua";  // Trả về tên template đúng
    }

    // Lưu thông tin đã chỉnh sửa
    @PostMapping("/sua")
    public String updateNganHang(@ModelAttribute NganHangDTO nganHangDTO, RedirectAttributes redirectAttributes) {
        nganHangService.updateNganHang(nganHangDTO);
        redirectAttributes.addFlashAttribute("message", "Thêm ngân hàng thành công!");
        return "redirect:/nganhang/list";
    }

    //    Xóa dữ liệu
    @PostMapping("/xoa/{id}")
    public String deleteNganHang(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Gọi phương thức deleteBaiDo từ service
            nganHangService.deleteNganHang(id);
            redirectAttributes.addFlashAttribute("message", "Xóa ngân hàng thành công!");
        } catch (IllegalArgumentException e) {
            // Thêm thông báo lỗi
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/nganhang/list"; // Chuyển hướng đến trang danh sách bãi đỗ
    }
}