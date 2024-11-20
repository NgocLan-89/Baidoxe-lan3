package com.example.baidoxe.service;

import com.example.baidoxe.dto.LoginDTO;
import org.apache.catalina.User;

import java.util.Optional;

public interface LoginService {
    boolean Login (String TaiKhoan ,String Password ,Integer roleId);
    LoginDTO Login2 (String TaiKhoan ,String Password ,Integer roleId);

}