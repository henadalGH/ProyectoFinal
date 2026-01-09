package com.example.wmotorproBack.wmotorBack.Servicio;


import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;

public interface VehiculoService {

    public ResponceDTO agregarVehiculo(VehiculoDTO vehiculoDTO, Long id)throws Exception;
    
    ResponceDTO obtenerVehiculoPorID(Long id);
}
