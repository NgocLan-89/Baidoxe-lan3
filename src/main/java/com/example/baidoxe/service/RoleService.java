package com.example.baidoxe.service;

import com.example.baidoxe.dto.RoleDTO;
import com.example.baidoxe.models.Role;

import java.util.List;

public interface RoleService {
    RoleDTO addRole(RoleDTO roleDTO);
    List<RoleDTO> findAllByStatus(Integer id);
    RoleDTO findAllById(Integer Id);
    RoleDTO updateRole(RoleDTO roleDTO);
}
