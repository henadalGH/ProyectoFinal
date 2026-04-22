package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;

public interface VehiculoService {

    public VehiculoEntity agregarVehiculo(VehiculoDTO vehiculoDTO);
    
    ResponceDTO obtenerVehiculoPorID(Long id);

    public VehiculoDTO mapToDto(VehiculoEntity vehiculo);

    public List<VehiculoDTO> ontenerVehiculoPorIdCliente(Long id);
}
