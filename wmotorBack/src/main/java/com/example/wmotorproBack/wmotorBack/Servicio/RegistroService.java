package com.example.wmotorproBack.wmotorBack.Servicio;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RegistroDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;

public interface RegistroService {

    public ResponceDTO registrarUsuario(RegistroDTO usuario) throws Exception;


}
