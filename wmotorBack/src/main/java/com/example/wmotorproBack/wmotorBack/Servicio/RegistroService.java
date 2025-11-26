package com.example.wmotorproBack.wmotorBack.Servicio;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;

public interface RegistroService {

    ResponceDTO register(UsuarioEntity usuario) throws Exception;

}
