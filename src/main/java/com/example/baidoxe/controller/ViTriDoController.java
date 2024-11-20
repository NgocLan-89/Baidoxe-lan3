package com.example.baidoxe.controller;

import com.example.baidoxe.dto.BaiDoDTO;
import com.example.baidoxe.dto.ViTriDoDTO;
import com.example.baidoxe.service.BaiDoService;
import com.example.baidoxe.service.ViTriDoService; // Nhớ import ViTriDoService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vitrido")
public class ViTriDoController {

    @Autowired
    private ViTriDoService viTriDoService;

    @Autowired
    private BaiDoService baiDoService;

    // Danh sách vị trí đỗ
    @GetMapping("/list")
    public String listViTriDo(Model model) {
        List<ViTriDoDTO> listViTriDo = viTriDoService.listActiveViTriDo(); // Lấy danh sách vị trí đỗ
        model.addAttribute("listViTriDo", listViTriDo);
        return "vitrido-list"; // Trả về view danh sách vị trí đỗ
    }

    // Thêm dữ liệu
    @GetMapping("/them")
    public String showAddViTriDoForm(Model model) {
        ViTriDoDTO viTriDoDTO = new ViTriDoDTO();
        model.addAttribute("viTriDoDTO", viTriDoDTO);
        model.addAttribute("baiDoList", baiDoService.findActiveBaiDo()); // Gọi danh sách bãi đỗ
        return "vitrido-them"; // Trả về view thêm vị trí đỗ
    }

    @PostMapping("/them")
    public String addViTriDo(@ModelAttribute ViTriDoDTO viTriDoDTO, RedirectAttributes redirectAttributes) {
        viTriDoService.addViTriDo(viTriDoDTO); // Gọi service để thêm vị trí đỗ
        redirectAttributes.addFlashAttribute("message", "Thêm vị trí đỗ thành công!");
        return "redirect:/vitrido/list"; // Chuyển hướng đến trang danh sách
    }

    // Sửa dữ liệu
    @GetMapping("/sua/{id}")
    public String editViTriDo(@PathVariable Integer id, Model model) {
        ViTriDoDTO viTriDoDTO = viTriDoService.findViTriDoById(id); // Gọi service để tìm vị trí đỗ theo ID
        model.addAttribute("viTriDoDTO", viTriDoDTO);
        model.addAttribute("baiDoList", baiDoService.findActiveBaiDo()); // Gọi danh sách bãi đỗ
        return "vitrido-sua"; // Trả về view sửa vị trí đỗ
    }

    // Lưu thông tin đã chỉnh sửa
    @PostMapping("/sua")
    public String updateViTriDo(@ModelAttribute ViTriDoDTO viTriDoDTO, RedirectAttributes redirectAttributes) {
        viTriDoService.updateViTriDo(viTriDoDTO.getId(), viTriDoDTO); // Gọi service để cập nhật vị trí đỗ
        redirectAttributes.addFlashAttribute("message", "Cập nhật vị trí đỗ thành công!");
        return "redirect:/vitrido/list"; // Chuyển hướng đến trang danh sách
    }

    // Xóa dữ liệu
    @PostMapping("/xoa/{id}")
    public String deleteViTriDo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            viTriDoService.deleteViTriDo(id); // Gọi service để xóa vị trí đỗ
            redirectAttributes.addFlashAttribute("message", "Xóa vị trí đỗ thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa: " + e.getMessage());
        }
        return "redirect:/vitrido/list"; // Chuyển hướng đến trang danh sách
    }
}
