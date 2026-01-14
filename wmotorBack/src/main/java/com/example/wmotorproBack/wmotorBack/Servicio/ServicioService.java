package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ServiciosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.ServicioEntity;

public interface ServicioService {


    List<ServicioEntity> ontenerTodosLosServicio();
    ServiciosDTO ontenerSercicioPorId(Long id);

}
