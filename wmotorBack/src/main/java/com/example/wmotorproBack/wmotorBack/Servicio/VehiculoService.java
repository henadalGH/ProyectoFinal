package com.example.wmotorproBack.wmotorBack.Servicio;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;

public interface VehiculoService {

    public VehiculoEntity agregarVehiculo(VehiculoDTO vehiculoDTO);
    
    ResponceDTO obtenerVehiculoPorID(Long id);
}
