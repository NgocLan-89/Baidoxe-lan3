package com.example.baidoxe.service;

import com.example.baidoxe.dto.UsersDTO;

import java.util.List;

public interface UserService {
    List<UsersDTO> listActiveUser(String status);
    UsersDTO findUserById(Integer Id);
    UsersDTO addUser(UsersDTO usersDTO);
    UsersDTO updateUser(UsersDTO usersDTO);
    void deleteUser(Integer Id);
}
