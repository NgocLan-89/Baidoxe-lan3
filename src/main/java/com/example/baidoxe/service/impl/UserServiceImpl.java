package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.UsersDTO;
import com.example.baidoxe.mapper.UsersMapper;
import com.example.baidoxe.models.NganHang;
import com.example.baidoxe.models.Role;
import com.example.baidoxe.models.Users;
import com.example.baidoxe.repository.UsersRepository;
import com.example.baidoxe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<UsersDTO> listActiveUser(String status) {
        try {
            int statusValue = Integer.parseInt(status); // Chuyển đổi từ String sang Integer
            return usersRepository.findActiveUsers(statusValue); // Gọi repository method
        } catch (NumberFormatException e) {
            // Xử lý nếu status không phải là một số
            throw new IllegalArgumentException("Status must be a valid integer");
        }
    }


    @Override
    public UsersDTO findUserById(Integer Id) {
        Optional<Users> userOptional = usersRepository.findById(Id);
        return userOptional.map(usersMapper::toUsersDTO).orElse(null); // Map to DTO if found
    }

    @Override
    public UsersDTO addUser(UsersDTO usersDTO) {
        Users users = usersMapper.toUsers(usersDTO); // Convert DTO to Entity
        Users savedUser = usersRepository.save(users); // Save the entity
        return usersMapper.toUsersDTO(savedUser); // Convert back to DTO and return
    }

    @Override
    public UsersDTO updateUser(UsersDTO usersDTO) {

        Optional<Users> userOptional = usersRepository.findById(usersDTO.getId());
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();

            // Cập nhật thông tin người dùng từ DTO
            existingUser.setHoTen(usersDTO.getHoTen());
            existingUser.setSDT(usersDTO.getSDT());
            existingUser.setEmail(usersDTO.getEmail());
            existingUser.setPassword(usersDTO.getPassword());
            existingUser.setTaiKhoan(usersDTO.getTaiKhoan());
            existingUser.setImage(usersDTO.getImage());

            // Cập nhật Role nếu có
            if (usersDTO.getRole_Id() != null) {
                Role role = new Role();
                role.setId(usersDTO.getRole_Id());
                role.setRole_name(usersDTO.getRole_name());
                existingUser.setRole(role);
            }

            // Cập nhật NganHang nếu có
            if (usersDTO.getNganHangId() != null) {
                NganHang nganHang = new NganHang();
                nganHang.setId(usersDTO.getNganHangId());
                nganHang.setTenNganHang(usersDTO.getTenNganHang());
                nganHang.setSoTaiKhoan(usersDTO.getSoTaiKhoan());
                existingUser.setNganHang(nganHang);
            }

            // Lưu thông tin cập nhật
            Users updatedUser = usersRepository.save(existingUser);
            return usersMapper.toUsersDTO(updatedUser); // Convert back to DTO
        } else {
            return null; // Nếu không tìm thấy user, trả về null hoặc ném ngoại lệ tùy ý
        }
    }

    @Override
    public void deleteUser(Integer Id) {
        // Tìm người dùng theo ID
        Optional<Users> userOptional = usersRepository.findById(Id);
        if (userOptional.isPresent()) {
            Users existingUser = userOptional.get();
            // Cập nhật trạng thái thành 0 (đã xóa)
            existingUser.setStatus(0); // Giả sử 0 đại diện cho trạng thái đã xóa
            usersRepository.save(existingUser); // Lưu thay đổi
        } else {
            throw new IllegalArgumentException("User not found with ID: " + Id); // Ném ngoại lệ nếu không tìm thấy
        }
    }
}