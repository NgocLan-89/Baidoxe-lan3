package com.example.baidoxe.controller;

import com.example.baidoxe.dto.LoginDTO;
import com.example.baidoxe.dto.LoginDTO;
import com.example.baidoxe.dto.RoleDTO;
import com.example.baidoxe.service.LoginService;
import com.example.baidoxe.service.RoleService;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/login")

public class loginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/auth")
    public String auth(Model model) {
        List<RoleDTO> roleDTOS=roleService.findAllByStatus(1);
        model.addAttribute("role",roleDTOS);
        return "login";
    }
    @GetMapping("/auth2")
    public String auth2(Model model) {
        return "kh-login";
    }
    @PostMapping("/auth")
    public String login(@RequestParam String taiKhoan,
                        @RequestParam String password,
                        @RequestParam Integer roleId,
                        HttpServletRequest request,
                        Model model) {
        LoginDTO loginDTO = loginService.Login2(taiKhoan, password, roleId);

        // Kiểm tra nếu đăng nhập thành công (loginDTO không null)
        if (loginDTO != null  && loginDTO.getIdRole()==1) {
            // Tạo session và lưu các giá trị idUser và roleId từ loginDTO vào session
            HttpSession session = request.getSession(true);
            session.setAttribute("IdUser", loginDTO.getIdUser());
            session.setAttribute("RoleId", roleId);

            // Thiết lập thời gian hết hạn session là 30 phút
            session.setMaxInactiveInterval(30 * 60);

            // Chuyển hướng đến trang home
            return "redirect:/map/list";
        }else if(loginDTO != null  && loginDTO.getIdRole()==2)
        {
            // Tạo session và lưu các giá trị idUser và roleId từ loginDTO vào session
            HttpSession session = request.getSession(true);
            session.setAttribute("IdUser", loginDTO.getIdUser());
            session.setAttribute("RoleId", roleId);

            // Thiết lập thời gian hết hạn session là 30 phút
            session.setMaxInactiveInterval(30 * 60);

            // Chuyển hướng đến trang home
            return "redirect:/baocao/thongke";
        }
        else {
            // Thêm thông báo lỗi vào model để hiển thị trên trang login
            model.addAttribute("errorMessage", "Đăng nhập không thành công! Vui lòng thử lại.");
            return "login";  // Trả về trang login và hiển thị thông báo lỗi
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Hủy session hiện tại
        HttpSession session = request.getSession(false); // Lấy session nếu tồn tại
        if (session != null) {
            session.invalidate(); // Hủy session
        }
        // Chuyển hướng về trang đăng nhập
        return "redirect:/login/auth";
    }



}