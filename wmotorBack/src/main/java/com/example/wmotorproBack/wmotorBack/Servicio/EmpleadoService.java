package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.EmpleadoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;

public interface EmpleadoService {

    List<EmpleadoEntity> getAllEmpleado();
    EmpleadoDTO getEmpleadoProId(Long id);
    
    
}
