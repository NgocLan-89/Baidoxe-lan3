package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.RoleDTO;
import com.example.baidoxe.mapper.RoleMapper;
import com.example.baidoxe.models.Role;
import com.example.baidoxe.repository.RoleRepository;
import com.example.baidoxe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO addRole(RoleDTO roleDTO)
    {
        Role role =roleMapper.toRole(roleDTO);
        if (role.getStatus() == null ) {
            role.setStatus(1);
        }
        Role saveRole=roleRepository.save(role);
        return roleMapper.toRoleDTO(saveRole);
    }

    @Override
    public List<RoleDTO> findAllByStatus(Integer id) {
        List<Role> roles=roleRepository.findAllByStatus(id);
        return roles.stream()
                .map(role -> roleMapper.toRoleDTO(role))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findAllById(Integer Id) {
        // Tìm kiếm đối tượng Role bằng id
        Role role = roleRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + Id)); // Nếu không tìm thấy, ném ngoại lệ

        // Chuyển đổi Role thành RoleDTO
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRoleName(role.getRole_name());
        roleDTO.setStatus(role.getStatus());

        // Trả về RoleDTO
        return roleDTO;
    }



    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        // Tìm kiếm Role trong cơ sở dữ liệu dựa trên id từ roleDTO
        Role suaRole = roleRepository.findById(roleDTO.getId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleDTO.getId()));

        // Cập nhật các trường thông tin từ roleDTO sang suaRole
        suaRole.setRole_name(roleDTO.getRoleName());
        suaRole.setStatus(1);

        // Lưu thay đổi vào  cơ sở dữ liệu
        roleRepository.save(suaRole);

        // Chuyển đổi suaRole sau khi cập nhật thành RoleDTO để trả về
        return roleMapper.toRoleDTO(suaRole);
    }



}
