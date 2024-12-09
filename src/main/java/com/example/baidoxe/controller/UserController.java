package com.example.baidoxe.controller;

import com.example.baidoxe.Provide.LuuAnh;
import com.example.baidoxe.dto.NganHangDTO;
import com.example.baidoxe.dto.PhuongTienDTO;
import com.example.baidoxe.dto.RoleDTO;
import com.example.baidoxe.dto.UsersDTO;
import com.example.baidoxe.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private NganHangService nganHangService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LuuAnh luuAnh;
    @GetMapping("/list")
    public String listUsersPage(HttpServletRequest request, Model model) {
        List<UsersDTO> listUser = userService.listActiveUser("1"); // Lấy danh sách user với status = 1 (Active)
        model.addAttribute("currentUrl", request.getRequestURI());
        model.addAttribute("listUser", listUser);
        return "user-list"; // Tên file HTML (không cần phần mở rộng .html)
    }
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        try {
            UsersDTO usersDTO = new UsersDTO();
            List<NganHangDTO> nganHangDTOList = nganHangService.listActiveNganHang();
            List<RoleDTO> roleDTOList = roleService.findAllByStatus(1);

            // In ra dữ liệu để kiểm tra
//            System.out.println("NganHangDTOList: " + nganHangDTOList);
//            System.out.println("RoleDTOList: " + roleDTOList);
//            System.out.println("usersDTO: " + usersDTO);

            model.addAttribute("usersDTO", usersDTO);
            model.addAttribute("nganHangList", nganHangDTOList);
            model.addAttribute("roleList", roleDTOList);

            return "user-them";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user-them";
        }
    }

    @PostMapping("/add")
    public String addUser(
            @Valid @ModelAttribute("usersDTO") UsersDTO usersDTO,
            @Valid @ModelAttribute("nganHangDTO") NganHangDTO nganHangDTO,
            BindingResult bindingResult,
            @RequestParam("photo") MultipartFile photo,  // Nhận file từ form
            Model model) {
        if (bindingResult.hasErrors()) {
            return "user-them";  // Nếu có lỗi validation
        }

        try {
            // Xử lý lưu file ảnh
            String photoPath = luuAnh.LuuAnhUser(photo);
            usersDTO.setImage(photoPath);

            // Check if we need to create a new NganHang
            if (nganHangDTO != null && nganHangDTO.getSoTaiKhoan() != null) {
                NganHangDTO addNganHang = nganHangService.addNganHang(nganHangDTO); // Add the new NganHang
                usersDTO.setNganHangId(addNganHang.getId()); // Set the ID of the newly created NganHang in the user
            }
            // Save the user
            UsersDTO addedUser = userService.addUser(usersDTO);

            return "redirect:/user/list";  // Chuyển hướng về danh sách user
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user-them";  // Trả về form thêm user nếu gặp lỗi
        }
    }

    @GetMapping("/sua/{Id}")
    public String showEditUserForm(@PathVariable("Id") Integer id, Model model) {
        try {
            UsersDTO usersDTO = userService.findUserById(id);
            List<NganHangDTO> nganHangDTOList = nganHangService.listActiveNganHang();
            List<RoleDTO> roleDTOList = roleService.findAllByStatus(1);

            model.addAttribute("usersDTO", usersDTO);
            model.addAttribute("nganHangList", nganHangDTOList);
            model.addAttribute("roleList", roleDTOList);

            return "user-sua";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/user/list"; // Redirect back to list if error occurs
        }
    }

    @PostMapping("/sua/{Id}")
    public String updateUser(
            @PathVariable("Id") Integer id,
            @Valid @ModelAttribute("usersDTO") UsersDTO usersDTO,
            @Valid @ModelAttribute("nganHangDTO") NganHangDTO nganHangDTO, // Add NganHangDTO for bank details
            BindingResult bindingResult,
            @RequestParam("photo") MultipartFile photo, // Optional photo upload
            Model model) {
        if (bindingResult.hasErrors()) {
            return "user-sua";  // Return to edit form if validation errors exist
        }

        try {
            // Handle optional photo upload
            if (!photo.isEmpty()) {
                String photoPath = luuAnh.LuuAnhUser(photo);
                usersDTO.setImage(photoPath); // Update the user's image
            }

            // Update or add NganHang if there are changes
            if (nganHangDTO != null && nganHangDTO.getSoTaiKhoan() != null) {
                // Update the bank information if modified
                NganHangDTO updatedNganHang = nganHangService.updateNganHang(nganHangDTO);
                usersDTO.setNganHangId(updatedNganHang.getId()); // Link updated NganHang ID to user
            }

            // Update user details
            userService.updateUser(usersDTO);

            return "redirect:/user/list"; // Redirect to user list after update
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user-sua";  // Return to edit form if update fails
        }
    }
    @GetMapping("/delete/{Id}")
    public String deleteUser(@PathVariable("Id") Integer id) {
        // Logic để xóa người dùng dựa trên ID
        userService.deleteUser(id);
        return "redirect:/user/list"; // hoặc trang danh sách người dùng
    }


}