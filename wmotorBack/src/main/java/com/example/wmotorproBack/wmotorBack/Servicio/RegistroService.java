package com.example.wmotorproBack.wmotorBack.Servicio;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RegistroDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.RolesEnum;

public interface RegistroService {

    public ResponceDTO registrarUsuario(RegistroDTO usuario, RolesEnum rol) throws Exception;


}
