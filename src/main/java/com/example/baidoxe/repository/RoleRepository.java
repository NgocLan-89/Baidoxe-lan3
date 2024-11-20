package com.example.baidoxe.repository;

import com.example.baidoxe.dto.RoleDTO;
import com.example.baidoxe.models.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    List<Role> findAllByStatus(Integer id);
    RoleDTO findAllById(Integer Id);
    RoleDTO findRoleById(Integer id);
}
