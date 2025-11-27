package com.example.wmotorproBack.wmotorBack.Servicio;


import java.util.HashMap;


import com.example.wmotorproBack.wmotorBack.Modelo.DTO.LoginDTO;




public interface AuthService {

    public HashMap<String , String> login(LoginDTO login) throws Exception;
    public void logout(String token);
    
}
