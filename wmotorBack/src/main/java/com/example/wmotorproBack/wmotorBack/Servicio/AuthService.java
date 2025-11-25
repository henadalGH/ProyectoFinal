package com.example.wmotorproBack.wmotorBack.Servicio;


import java.util.HashMap;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.LoginDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;



public interface AuthService {

    public HashMap<String , String> login(LoginDTO login) throws Exception;
    
    public ResponceDTO register( UsuarioEntity usuario) throws Exception;
}
