package com.example.baidoxe.controller;

import com.example.baidoxe.dto.PhuongTienDTO;
import com.example.baidoxe.service.PhuongTienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/KH")
public class PhuongTienController {

    @Autowired
    private PhuongTienService phuongTienService;

    // Hiển thị tất cả phương tiện của người dùng
    @GetMapping("/phuongtien")
    public String getPhuongTienByUserId(Model model, HttpSession session) {
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");
        // Kiểm tra xem session có hợp lệ không
        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
        }
        List<PhuongTienDTO> phuongTienDTO=phuongTienService.findPhuongTienUser(idUser);
        model.addAttribute("phuongTienList", phuongTienDTO);

        return "KH/phuongtien";
    }

    // Hiển thị form thêm phương tiện
    @GetMapping("/phuongtien/them")
    public String showAddPhuongTienForm(Model model, HttpSession session) {
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");

        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Redirect đến trang login nếu session không hợp lệ
        }

        // Tạo một PhuongTienDTO trống để binding vào form
        PhuongTienDTO phuongTienDTO = new PhuongTienDTO();
        model.addAttribute("phuongTienDTO", phuongTienDTO);

        return "KH/phuongtien-them";
    }

    // Thêm phương tiện
    @PostMapping("/phuongtien/them")
    public String addPhuongTien(@ModelAttribute PhuongTienDTO phuongTienDTO, HttpSession session) {
        // Lấy giá trị từ session
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");

        // Kiểm tra xem session có hợp lệ không
        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
        }

        phuongTienDTO.setUserId(idUser); // Đảm bảo userId từ session
        phuongTienService.addPhuongTien(phuongTienDTO);
        return "redirect:/KH/phuongtien";
    }

    // Hiển thị form sửa phương tiện
    @GetMapping("/phuongtien/sua/{id}")
    public String showEditPhuongTienForm(@PathVariable Integer id, Model model, HttpSession session) {
        // Lấy giá trị từ session
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");

        // Kiểm tra xem session có hợp lệ không
        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
        }
        PhuongTienDTO phuongTienDTO = phuongTienService.findById(id, idUser); // Lấy phương tiện theo id và userId
        if (phuongTienDTO == null) {
            return "redirect:/KH/phuongtien"; // Điều hướng nếu không tìm thấy phương tiện
        }

        model.addAttribute("phuongTienDTO", phuongTienDTO);
        return "KH/phuongtien-sua";
    }

    // Sửa phương tiện
    @PostMapping("/phuongtien/sua")
    public String editPhuongTien(@ModelAttribute PhuongTienDTO phuongTienDTO, HttpSession session) {
        // Lấy giá trị từ session
        Integer idUser = (Integer) session.getAttribute("IdUser");
        Integer roleId = (Integer) session.getAttribute("RoleId");

        // Kiểm tra xem session có hợp lệ không
        if (idUser == null || roleId == null) {
            return "redirect:/login/auth"; // Chuyển hướng đến trang đăng nhập nếu session không hợp lệ
        }
        phuongTienDTO.setUserId(idUser); // Đảm bảo userId từ session
        phuongTienService.updatePhuongTien(phuongTienDTO);
        return "redirect:/KH/phuongtien";

    }

    // Xóa phương tiện
    @GetMapping("/phuongtien/xoa/{id}/{userId}")
    public String deletePhuongTien(@PathVariable Integer id, @PathVariable Integer userId, HttpSession session) {
        // Logic to check session and delete PhuongTien
        phuongTienService.deletePhuongTien(id, userId);
        return "redirect:/KH/phuongtien";
    }

}