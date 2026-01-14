package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;

public interface RepuestoService {

    List<RepuestosEntity> getAllRepuesto();
    RepuestoDTO getRepuestoPorId(Long id);

}
