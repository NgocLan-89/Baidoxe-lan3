package com.example.baidoxe.service.impl;

import com.example.baidoxe.dto.LoginDTO;
import com.example.baidoxe.repository.LoginRepository;
import com.example.baidoxe.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginRepository loginRepository;
    @Override
    public boolean Login(String TaiKhoan, String Password, Integer roleId) {
        Optional<LoginDTO> loginDTO =loginRepository.findUserLogin(TaiKhoan,Password,roleId);
        return loginDTO.isPresent();
    }

    @Override
    public LoginDTO Login2(String TaiKhoan, String Password, Integer roleId) {
        Optional<LoginDTO> loginDTO =loginRepository.findUserLogin(TaiKhoan,Password,roleId);
        if(loginDTO.isPresent())
        {
            return loginDTO.get();
        }
        else {
            return null;
        }
    }
}