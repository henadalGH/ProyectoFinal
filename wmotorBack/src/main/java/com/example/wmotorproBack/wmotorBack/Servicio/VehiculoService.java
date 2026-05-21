package com.example.wmotorproBack.wmotorBack.Servicio;

import java.util.List;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.VehiculoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.VehiculoEntity;

public interface VehiculoService {

    public VehiculoEntity agregarVehiculo(VehiculoDTO vehiculoDTO);
    
    VehiculoDTO obtenerVehiculoPorID(Long id);

    public VehiculoDTO mapToVehiculoDto(VehiculoEntity vehiculo);

    public List<VehiculoDTO> ontenerVehiculoPorIdCliente(Long id);
}
