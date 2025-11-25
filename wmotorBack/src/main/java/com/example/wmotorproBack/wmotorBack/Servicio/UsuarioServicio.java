package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.Entity.UsuarioEntity;

public interface UsuarioServicio {

    public List<UsuarioEntity> findAllUsers();
}
