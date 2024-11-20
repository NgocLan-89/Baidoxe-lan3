package com.example.baidoxe.mapper;

import com.example.baidoxe.dto.RoleDTO;
import com.example.baidoxe.models.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleDTO toRoleDTO(Role role)
    {
        if(role==null)
        {
            return null;
        }
        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getRole_name())
                .status(role.getStatus())
                .build();

    }
    public Role toRole(RoleDTO roleDTO)
    {
        if(roleDTO==null)
        {
            return null;
        }
        Role role=new Role();
        role.setId(roleDTO.getId());
        role.setRole_name(roleDTO.getRoleName());
        role.setStatus(roleDTO.getStatus());
        return role;

    }
}
